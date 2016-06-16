package com.osprey.marketdata.batch;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.osprey.marketdata.batch.listener.JobCompletionNotificationListener;
import com.osprey.marketdata.batch.processor.HotShitScreenProcessor;
import com.osprey.marketdata.batch.processor.InitialScreenProcessor;
import com.osprey.marketdata.batch.processor.QuoteProcessor;
import com.osprey.marketdata.feed.ISecurityMasterService;
import com.osprey.screen.ScreenSuccessSecurity;
import com.osprey.securitymaster.ExtendedFundamentalPricedSecurityWithHistory;
import com.osprey.securitymaster.Security;

@Configuration
@EnableBatchProcessing
@PropertySource("classpath:config.properties")
public class NightlyMarketDataScreen {

	final static Logger logger = LogManager.getLogger(NightlyMarketDataScreen.class);

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private ISecurityMasterService securityMasterService;

	// A queue to temporarily hold securities to quote and pull history for.
	private final ConcurrentLinkedQueue<Security> postInitialScreenResultQueue = new ConcurrentLinkedQueue<>(); // #1
	private final ConcurrentLinkedQueue<ExtendedFundamentalPricedSecurityWithHistory> postQuoteQueue = new ConcurrentLinkedQueue<>(); // #2


	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);// TODO extract
		executor.setMaxPoolSize(8); // TODO extract
		executor.afterPropertiesSet();
		return executor;
	}
	
	@Bean
	public ListItemReader<Security> initialScreenReader() {
		logger.info("Featching the security master for {} ... ", () -> LocalDate.now());
		Set<Security> fetchSecurityMaster = securityMasterService.fetchSecurityMaster();
		return new ListItemReader<>(new ArrayList<>(fetchSecurityMaster));
	}
	
	@Bean
	public ItemReader<Security> postInitialScreenQueueReader() {
		return new ItemReader<Security>() {

			@Override
			public Security read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

				Security security = postInitialScreenResultQueue.poll();
				logger.debug("Fetching {} from the postInitialScreenResultQueue for processing", () -> security);
				return security;
			}

		};
	}

	@Bean
	public ItemReader<ExtendedFundamentalPricedSecurityWithHistory> postQuoteQueueReader() {
		return new ItemReader<ExtendedFundamentalPricedSecurityWithHistory>() {

			@Override
			public ExtendedFundamentalPricedSecurityWithHistory read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

				ExtendedFundamentalPricedSecurityWithHistory security = postQuoteQueue.poll();
				logger.debug("Fetching {} from the postQuoteQueue for processing", () -> security);
				return security;
			}

		};
	}

	@Bean
	public InitialScreenProcessor initialScreenProcessor() {
		return new InitialScreenProcessor();
	}
	
	@Bean
	public QuoteProcessor quoteProcessor() {
		return new QuoteProcessor();
	}
	
	@Bean
	public HotShitScreenProcessor hotShitScreenProcessor() {
		return new HotShitScreenProcessor();
	}

	@Bean
	public ItemWriter<Security> initialScreenQueueWriter() {
		return new ItemWriter<Security>() {

			@Override
			public void write(List<? extends Security> items) throws Exception {
				logger.debug("Queuing securities for further quoting {}", () -> items);
				postInitialScreenResultQueue.addAll(items);
			}

		};
	}	
	
	@Bean
	public ItemWriter<ExtendedFundamentalPricedSecurityWithHistory> postQuoteQueueWriter() {
		return new ItemWriter<ExtendedFundamentalPricedSecurityWithHistory>() {

			@Override
			public void write(List<? extends ExtendedFundamentalPricedSecurityWithHistory> items) throws Exception {
				logger.debug("Queuing securities for further screening {}", () -> items);
				postQuoteQueue.addAll(items);
			}

		};
	}

	@Bean
	MongoItemWriter<ScreenSuccessSecurity> hotListMongoItemWriter() {
		MongoItemWriter<ScreenSuccessSecurity> mongoItemWriter = new MongoItemWriter<>();
		mongoItemWriter.setTemplate(mongoTemplate);

		return mongoItemWriter;
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener(new JdbcTemplate(dataSource));
	}

	@Bean
	public Job processNightlySecurityMaster() {
		return jobBuilderFactory.get("nightlySecurityMasterProcess")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(initialScreen()) // #1
				.next(quoteAndCalcAndPersist()) // #2
				.next(hotListFilterAndPersist()) // #3
				.end().build();
	}

	@Bean
	public Step initialScreen() {
		return stepBuilderFactory
				.get("preScreen")
				.<Security, Security>chunk(250)
				.reader(initialScreenReader())
				.processor(initialScreenProcessor())
				.writer(initialScreenQueueWriter())
			//	.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	public Step quoteAndCalcAndPersist() {
		return stepBuilderFactory.get("quoteAndCalc")
				 .<Security, ExtendedFundamentalPricedSecurityWithHistory>chunk(5)
				 .reader(postInitialScreenQueueReader()) // Read From the postInitialScreenQueue
				 .processor(quoteProcessor()) // Pull fundamentals, history, and calculate desired points (oVol, ema, sma, etc... )
				 .writer(postQuoteQueueWriter()) // Cache the into a post quote queue
				// .writer(initialScreenWriter()) // Write the results to postgresql
				// .taskExecutor(taskExecutor())
				 .build();
	}

	// TODO This needs to be heavily profiled for memory usage and time. This
	// many need to use JMS to split out the work depending on time.

	@Bean
	public Step hotListFilterAndPersist() {
		return stepBuilderFactory.get("hotListFilter")
				.<ExtendedFundamentalPricedSecurityWithHistory, ScreenSuccessSecurity>chunk(100)
				.reader(postQuoteQueueReader()) // read the post quote queue
				.processor(hotShitScreenProcessor()) // Run the additional screens for the hot list
				// .writer(initialScreenWriter()) // persist the hot list to postgresql
				.writer(hotListMongoItemWriter()) // persist the hot list to mongodb
				//.taskExecutor(taskExecutor())
				.build();
	}

}
