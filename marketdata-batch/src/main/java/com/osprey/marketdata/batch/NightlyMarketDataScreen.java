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
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.osprey.marketdata.batch.listener.JobCompletionNotificationListener;
import com.osprey.marketdata.batch.processor.InitialScreenProcessor;
import com.osprey.marketdata.feed.ISecurityMasterService;
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
	private ISecurityMasterService securityMasterService;

	// A queue to temporarily hold securities to quote and pull history for.
	private final ConcurrentLinkedQueue<Security> postInitialScreenResultQueue = new ConcurrentLinkedQueue<>();

	@Bean
	public ListItemReader<Security> initialScreenReader() {
		logger.info("Featching the security master for {} ... ", () -> LocalDate.now());
		Set<Security> fetchSecurityMaster = securityMasterService.fetchSecurityMaster();
		return new ListItemReader<>(new ArrayList<>(fetchSecurityMaster));
	}

	@Bean
	public InitialScreenProcessor initialScreenProcessor() {
		return new InitialScreenProcessor();
	}

	@Bean
	public ItemWriter<Security> initialScreenWriter() {
		return new ItemWriter<Security>() {

			@Override
			public void write(List<? extends Security> items) throws Exception {
				logger.info("Queuing securities for further quoting {}", () -> items);
				postInitialScreenResultQueue.addAll(items);
			}

		};
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener(new JdbcTemplate(dataSource));
	}

	@Bean
	public Job processNightlySecurityMaster() {
		return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(initialScreen()) // #1
				.next(quoteAndCalcAndPersist()) // #2
				.next(hotListFilterAndPersist()) // #3
				.end()
				.build();
	}

	@Bean
	public Step initialScreen() {
		return stepBuilderFactory
				.get("preScreen")
				.<Security, Security>chunk(250)
				.reader(initialScreenReader())
				.processor(initialScreenProcessor())
				.writer(initialScreenWriter())
				.build();
	}
	
	@Bean
	public Step quoteAndCalcAndPersist() {
		return stepBuilderFactory
				.get("quoteAndCalc")
				.<Security, ExtendedFundamentalPricedSecurityWithHistory>chunk(5)
			//	.reader(initialScreenReader()) // Read From the postInitialScreenQueue
			//	.processor(initialScreenProcessor()) // Pull fundamentals, history, and calculate desired points (oVol, ema, sma, etc ... )
			//	.writer(initialScreenWriter()) // Cache the into a post quote queue
			//	.writer(initialScreenWriter()) // Write the results to postgresql
				.build();
	}
	
	// TODO This needs to be heavily profiled for memory usage and time. This
	// many need to use JMS to split out the work depending on time.

	@Bean
	public Step hotListFilterAndPersist() {
		return stepBuilderFactory
				.get("hotListFilter")
				.<ExtendedFundamentalPricedSecurityWithHistory, ExtendedFundamentalPricedSecurityWithHistory>chunk(100)
			//	.reader(initialScreenReader()) // read the post quote queue
			//	.processor(initialScreenProcessor()) // Run the additional screens for the hot list
			//	.writer(initialScreenWriter()) // persist the hot list to postgresql
			//	.writer(initialScreenWriter()) // persist the hot list to mongodb
				.build();
	}

}
