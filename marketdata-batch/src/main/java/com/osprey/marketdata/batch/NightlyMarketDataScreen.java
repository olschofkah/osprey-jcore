package com.osprey.marketdata.batch;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

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
import com.osprey.marketdata.batch.processor.lmax.ThrottleDisruptor;
import com.osprey.marketdata.batch.reader.SecurityMasterItemReader;
import com.osprey.marketdata.batch.tasklet.QuoteThrottleDisruptorShutdownTasklet;
import com.osprey.marketdata.batch.tasklet.QuoteThrottleDisruptorStartupTasklet;
import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
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

	// A queue to temporarily hold securities to quote and pull history for.
	private final ConcurrentLinkedQueue<Security> postInitialScreenResultQueue = new ConcurrentLinkedQueue<>(); // #1
	private final ConcurrentLinkedQueue<ExtendedFundamentalPricedSecurityWithHistory> postQuoteQueue = new ConcurrentLinkedQueue<>(); // #2

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(4);// TODO extract
		executor.setMaxPoolSize(8); // TODO extract
		executor.setThreadFactory(threadFactory());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

	@Bean
	public ThreadFactory threadFactory() {
		return Executors.defaultThreadFactory();
	}

	@Bean
	public ThrottleDisruptor throttleDisruptor() {
		return new ThrottleDisruptor(throttleCapacity(), threadFactory());
	}

	@Bean
	public AtomicLong throttleCapacity() {
		return new AtomicLong();
	}

	@Bean
	public SecurityMasterItemReader initialScreenReader() {
		return new SecurityMasterItemReader();
	}

	@Bean
	public ItemReader<Security> postInitialScreenQueueReader() {
		return new ItemReader<Security>() {

			@Override
			public Security read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

				Security security = postInitialScreenResultQueue.poll();
				logger.debug("Fetching {} from the postInitialScreenResultQueue for processing",
						() -> security.getSymbol());
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
	public QuoteThrottleDisruptorShutdownTasklet quoteThrottleDisruptorShutdownTasklet() {
		return new QuoteThrottleDisruptorShutdownTasklet();
	}

	@Bean
	public QuoteThrottleDisruptorStartupTasklet quoteThrottleDisruptorStartupTasklet(){
		return new QuoteThrottleDisruptorStartupTasklet();
	}
	
	
	@Bean
	public ItemWriter<Security> initialScreenQueueWriter() {
		return new ItemWriter<Security>() {

			@Override
			public void write(List<? extends Security> items) throws Exception {
				logger.debug("Queuing securities for further quoting ...");
				postInitialScreenResultQueue.addAll(items);
			}

		};
	}

	@Bean
	public ItemWriter<ExtendedFundamentalPricedSecurityWithHistory> postQuoteQueueWriter() {
		return new ItemWriter<ExtendedFundamentalPricedSecurityWithHistory>() {

			@Override
			public void write(List<? extends ExtendedFundamentalPricedSecurityWithHistory> items) throws Exception {
				logger.debug("Queuing securities for further screening ...");
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
		return jobBuilderFactory.get("nightlySecurityMasterProcess").incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(initialScreen()) // #1
				.next(disruptorStartup()) // #2
				.next(quoteAndCalcAndPersist()) // #3
				.next(disruptorShutdown()) // #4
				.next(hotListFilterAndPersist()) // #5
				.end()
				.build();
	}

	@Bean
	public Step initialScreen() {
		return stepBuilderFactory.get("preScreen")
				.<Security, Security>chunk(250)
				.faultTolerant()
				.retryLimit(5)
				.retry(MarketDataIOException.class)
				.reader(initialScreenReader())
				.processor(initialScreenProcessor())
				.writer(initialScreenQueueWriter())
				.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	public Step quoteAndCalcAndPersist() {
		return stepBuilderFactory.get("quoteAndCalc")
				.<Security, ExtendedFundamentalPricedSecurityWithHistory>chunk(5)
				.reader(postInitialScreenQueueReader())
				.faultTolerant()
				.retryLimit(5)
				.retry(MarketDataIOException.class)
				.skipLimit(25)
				.skip(MarketDataNotAvailableException.class)
				// .skipLimit(25) // needs to specify what can be skipped. 
				.processor(quoteProcessor())
				.writer(postQuoteQueueWriter())
				// .writer(persistStats)
				//.taskExecutor(taskExecutor())
				.build();
	}
	
	@Bean
	public Step disruptorShutdown() {
		return stepBuilderFactory.get("disruptorShutdown")
				.tasklet(quoteThrottleDisruptorShutdownTasklet())
				.build();
	}
	
	@Bean
	public Step disruptorStartup() {
		return stepBuilderFactory.get("disruptorStartup")
				.tasklet(quoteThrottleDisruptorStartupTasklet())
				.build();
	}

	@Bean
	public Step hotListFilterAndPersist() {
		return stepBuilderFactory.get("hotListFilter")
				.<ExtendedFundamentalPricedSecurityWithHistory, ScreenSuccessSecurity>chunk(100)
				.reader(postQuoteQueueReader())
				.processor(hotShitScreenProcessor())
				// .writer(peristHotList)
				// .writer(hotListMongoItemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}

}
