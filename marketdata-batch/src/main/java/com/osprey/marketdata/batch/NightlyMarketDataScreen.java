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
				.flow(initialScreen())
				.end()
				.build();
	}

	@Bean
	public Step initialScreen() {
		return stepBuilderFactory
				.get("preScreen")
				.<Security, Security>chunk(10)
				.reader(initialScreenReader())
				.processor(initialScreenProcessor())
				.writer(initialScreenWriter())
				.build();
	}

}
