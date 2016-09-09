package com.osprey.marketdata.batch.events;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.integration.slack.SlackClient;
import com.osprey.marketdata.batch.events.listener.JobCompletionNotificationListener;
import com.osprey.marketdata.batch.events.processor.EventsProcessor;
import com.osprey.marketdata.batch.events.reader.SecurityQuoteContainerItemReader;
import com.osprey.marketdata.batch.events.tasklet.QuoteThrottleDisruptorShutdownTasklet;
import com.osprey.marketdata.batch.events.tasklet.QuoteThrottleDisruptorStartupTasklet;
import com.osprey.marketdata.batch.events.writer.SecurityQuoteContainerItemWriter;
import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.marketdata.feed.ychart.YChartHistoricalEventsClient;
import com.osprey.marketdata.throttle.ThrottleDisruptor;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;
import com.osprey.securitymaster.repository.jdbctemplate.SecurityMasterJdbcRepository;

@Configuration
@EnableBatchProcessing
public class SecurityEventsBatchJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private PlatformTransactionManager txnManager;

	@Autowired
	@Qualifier("postgresDataSource")
	private DataSource dataSource;

	@Autowired
	private RestTemplate http;

	@Autowired
	private SlackClient slack;
	
	@Autowired
	@Qualifier("om1")
	private ObjectMapper om1;

	@Value("${threads.core.pool.size}")
	private int corePoolSize;
	@Value("${threads.max.pool.size}")
	private int maxPoolSize;
	@Value("${threads.keep.alive.seconds}")
	private int keepAliveSeconds;

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setThreadFactory(threadFactory());
		executor.setAllowCoreThreadTimeOut(false);
		executor.setKeepAliveSeconds(keepAliveSeconds);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		return executor;
	}

	@Bean
	public ExecutorService executorService() {
		return ((ThreadPoolTaskExecutor) taskExecutor()).getThreadPoolExecutor();
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
	public YChartHistoricalEventsClient yChartClient() {
		return new YChartHistoricalEventsClient(http);
	}

	@Bean
	public ISecurityMasterRepository securityMasterRepository() {
		return new SecurityMasterJdbcRepository(dataSource);
	}

	@Bean
	public SecurityQuoteContainerItemReader quoteItemReader() {
		return new SecurityQuoteContainerItemReader(securityMasterRepository(), executorService());
	}

	@Bean
	public EventsProcessor quoteProcessor() {
		return new EventsProcessor(yChartClient(), throttleCapacity());
	}

	@Bean
	public QuoteThrottleDisruptorShutdownTasklet quoteThrottleDisruptorShutdownTasklet() {
		return new QuoteThrottleDisruptorShutdownTasklet(throttleDisruptor());
	}

	@Bean
	public QuoteThrottleDisruptorStartupTasklet quoteThrottleDisruptorStartupTasklet() {
		return new QuoteThrottleDisruptorStartupTasklet(throttleDisruptor());
	}

	@Bean
	public SecurityQuoteContainerItemWriter quoteContainerItemWriter() {
		return new SecurityQuoteContainerItemWriter(securityMasterRepository());
	}

	@Bean
	public ExponentialBackOffPolicy exponentialBackOffPolicy() {
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(1000);
		return backOffPolicy;
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener(slack);
	}

	@Bean
	@Scope("prototype")
	public Job processNightlySecurityMaster() {
		return jobBuilderFactory.get("securityEventsProcessBatchJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(disruptorStartup()) 
				.next(processEvents())
				.next(disruptorShutdown())
				.end()
				.build();
	}

	@Bean
	public Step processEvents() {
		return stepBuilderFactory.get("processEvents")
				.allowStartIfComplete(true)
				.<SecurityQuoteContainer, SecurityQuoteContainer>chunk(8)
				.reader(quoteItemReader())
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
				.taskExecutor(taskExecutor())
				.throttleLimit(4)
				.build();
	}

	@Bean
	public Step disruptorShutdown() {
		return stepBuilderFactory.get("disruptorShutdown")
				.allowStartIfComplete(true)
				.tasklet(quoteThrottleDisruptorShutdownTasklet())
				.build();
	}

	@Bean
	public Step disruptorStartup() {
		return stepBuilderFactory.get("disruptorStartup")
				.allowStartIfComplete(true)
				.tasklet(quoteThrottleDisruptorStartupTasklet())
				.build();
	}

	

}
