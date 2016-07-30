package com.osprey.marketdata.batch;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.integration.slack.SlackClient;
import com.osprey.marketdata.batch.listener.JobCompletionNotificationListener;
import com.osprey.marketdata.batch.processor.HotShitScreenProcessor;
import com.osprey.marketdata.batch.processor.HotShitScreenProvidor;
import com.osprey.marketdata.batch.processor.InitialScreenProcessor;
import com.osprey.marketdata.batch.processor.QuoteProcessor;
import com.osprey.marketdata.batch.processor.lmax.ThrottleDisruptor;
import com.osprey.marketdata.batch.reader.SecurityMasterItemReader;
import com.osprey.marketdata.batch.tasklet.PurgePreviousRunHotlistTasklet;
import com.osprey.marketdata.batch.tasklet.QuoteThrottleDisruptorShutdownTasklet;
import com.osprey.marketdata.batch.tasklet.QuoteThrottleDisruptorStartupTasklet;
import com.osprey.marketdata.batch.writer.HotShitDbItemWriter;
import com.osprey.marketdata.batch.writer.QuoteContainerItemWriter;
import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.HotListItem;
import com.osprey.screen.repository.IHotShitRepository;
import com.osprey.screen.repository.IOspreyJSONObjectRepository;
import com.osprey.screen.repository.jdbctemplate.HotShitJdbcRepository;
import com.osprey.screen.repository.jdbctemplate.OspreyJSONObjectJdbcRepository;
import com.osprey.screen.service.BlackListService;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;
import com.osprey.securitymaster.repository.jdbctemplate.SecurityMasterJdbcRepository;

@Configuration
@EnableBatchProcessing
public class NightlyMarketDataScreen {

	final static Logger logger = LogManager.getLogger(NightlyMarketDataScreen.class);

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private PlatformTransactionManager txnManager;

	@Autowired
	private DataSource dataSource;

	// A queue to temporarily hold securities to quote and pull history for.
	private final ConcurrentLinkedQueue<SecurityQuoteContainer> postInitialScreenResultQueue = new ConcurrentLinkedQueue<>(); // #1
	private final ConcurrentLinkedQueue<SecurityQuoteContainer> postQuoteQueue = new ConcurrentLinkedQueue<>(); // #2
	
	@Bean
	public DataSource postgresDataSource() {
		return DataSourceBuilder.create()
				.url("jdbc:postgresql://ospreydb.cl1fkmenjbzm.us-east-1.rds.amazonaws.com:5432/osprey01")
				.username("ospreyjavausr")
				.password("F4&^mfWXqazY")
				.type(BasicDataSource.class)
				.build();

		// TODO EXTRACT TO CONFIG !!!
	}
	
	@Bean
	public ObjectMapper om1(){
		return new ObjectMapper();
	}

	@Bean
	public SlackClient slackClient() {
		return new SlackClient();
	}

	
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
	public IOspreyJSONObjectRepository ospreyJSONObjectRepository() {
		return new OspreyJSONObjectJdbcRepository(dataSource);
	}

	@Bean
	public IHotShitRepository hotShitRepository() {
		return new HotShitJdbcRepository(dataSource);
	}

	@Bean
	public ISecurityMasterRepository securityMasterRepository(){
		return new SecurityMasterJdbcRepository(dataSource);
	}

	@Bean
	public SecurityMasterItemReader initialScreenReader() {
		return new SecurityMasterItemReader();
	}
	
	@Bean
	public HotShitScreenProvidor hotShitScreenProvidor(){
		return new HotShitScreenProvidor();
	}
	
	@Bean
	public BlackListService blackListService(){
		return new BlackListService();
	}

	@Bean
	public ItemReader<SecurityQuoteContainer> postInitialScreenQueueReader() {
		return new ItemReader<SecurityQuoteContainer>() {

			@Override
			public SecurityQuoteContainer read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

				SecurityQuoteContainer security = postInitialScreenResultQueue.poll();
				logger.debug("Fetching {} from the postInitialScreenResultQueue for processing",
						() -> security == null ? null : security.getKey().getSymbol());
				return security;
			}

		};
	}

	@Bean
	public ItemReader<SecurityQuoteContainer> postQuoteQueueReader() {
		return new ItemReader<SecurityQuoteContainer>() {

			@Override
			public SecurityQuoteContainer read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

				SecurityQuoteContainer security = postQuoteQueue.poll();
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
	public PurgePreviousRunHotlistTasklet purgePreviousRunHotlistTasklet() {
		return new PurgePreviousRunHotlistTasklet();
	}

	@Bean
	public QuoteThrottleDisruptorShutdownTasklet quoteThrottleDisruptorShutdownTasklet() {
		return new QuoteThrottleDisruptorShutdownTasklet();
	}

	@Bean
	public QuoteThrottleDisruptorStartupTasklet quoteThrottleDisruptorStartupTasklet() {
		return new QuoteThrottleDisruptorStartupTasklet();
	}

	@Bean
	public ItemWriter<SecurityQuoteContainer> initialScreenQueueWriter() {
		return new ItemWriter<SecurityQuoteContainer>() {

			@Override
			public void write(List<? extends SecurityQuoteContainer> items) throws Exception {
				logger.debug("Queuing securities for further quoting ...");
				postInitialScreenResultQueue.addAll(items);
			}

		};
	}

	@Bean
	public HotShitDbItemWriter hotShitDbItemWriter() {
		return new HotShitDbItemWriter();
	}
	
	@Bean QuoteContainerItemWriter quoteContainerItemWriter(){
		return new QuoteContainerItemWriter(postQuoteQueue);
	}

	@Bean
	public ExponentialBackOffPolicy exponentialBackOffPolicy() {
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(1000);
		return backOffPolicy;
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener();
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	@Bean
	@Scope("prototype")
	public Job processNightlySecurityMaster() {
		return jobBuilderFactory.get("nightlySecurityMasterProcess").incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(initialScreen()) // #1 ... this starts it
				.next(disruptorStartup()) // #2
				.next(quoteAndCalcAndPersist()) // #3
				.next(disruptorShutdown()) // #4
				.next(deletePreviousHotlistForToday()) // #5
				.next(hotListFilterAndPersist()) // #6
				.end().build();
	}

	@Bean
	public Step initialScreen() {
		return stepBuilderFactory.get("preScreen")
				.<Security, SecurityQuoteContainer>chunk(250)
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
				.<SecurityQuoteContainer, SecurityQuoteContainer>chunk(1)
				.reader(postInitialScreenQueueReader())
				.faultTolerant()
				.backOffPolicy(exponentialBackOffPolicy())
				.retryLimit(5)
				.retry(MarketDataIOException.class)
				.skipLimit(25)
				.skip(MarketDataNotAvailableException.class)
				.skip(MarketDataIOException.class)
				.processor(quoteProcessor())
				.writer(quoteContainerItemWriter())
				.transactionManager(txnManager)
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
	public Step deletePreviousHotlistForToday() {
		return stepBuilderFactory.get("hotListDestroyer")
				.tasklet(purgePreviousRunHotlistTasklet())
				.build();
	}

	@Bean
	public Step hotListFilterAndPersist() {
		return stepBuilderFactory.get("hotListFilter")
				.<SecurityQuoteContainer, HotListItem>chunk(100)
				.faultTolerant()
				.skip(InsufficientHistoryException.class)
				.skipLimit(256)
				.reader(postQuoteQueueReader())
				.processor(hotShitScreenProcessor())
				.writer(hotShitDbItemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}

}
