package com.osprey.marketdata.batch;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.integration.slack.SlackClient;
import com.osprey.marketdata.batch.listener.JobCompletionNotificationListener;
import com.osprey.marketdata.batch.processor.HistoricalModelProcessor;
import com.osprey.marketdata.batch.processor.HotShitScreenProcessor;
import com.osprey.marketdata.batch.processor.HotShitScreenProvidor;
import com.osprey.marketdata.batch.processor.InitialScreenService;
import com.osprey.marketdata.batch.processor.QuoteProcessor;
import com.osprey.marketdata.batch.processor.lmax.ThrottleDisruptor;
import com.osprey.marketdata.batch.reader.QuoteItemReader;
import com.osprey.marketdata.batch.reader.SecurityMasterItemReader;
import com.osprey.marketdata.batch.tasklet.MarketDataLoadDecider;
import com.osprey.marketdata.batch.tasklet.NoOpTasklet;
import com.osprey.marketdata.batch.tasklet.PurgePreviousRunHotlistTasklet;
import com.osprey.marketdata.batch.tasklet.QuoteThrottleDisruptorShutdownTasklet;
import com.osprey.marketdata.batch.tasklet.QuoteThrottleDisruptorStartupTasklet;
import com.osprey.marketdata.batch.writer.HotShitDbItemWriter;
import com.osprey.marketdata.batch.writer.QuoteContainerItemWriter;
import com.osprey.marketdata.feed.constants.QuoteDataFrequency;
import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.marketdata.feed.nasdaq.NasdaqSecurityMasterFtpService;
import com.osprey.marketdata.feed.yahoo.YahooHistoricalQuoteClient;
import com.osprey.marketdata.feed.yahoo.YahooHistoricalUrlBuilder;
import com.osprey.marketdata.feed.yahoo.YahooQuoteClient;
import com.osprey.marketdata.feed.yahoo.YahooQuoteUrlBuilder;
import com.osprey.marketdata.service.MarketDataLoadDateService;
import com.osprey.marketdata.service.MarketScheduleService;
import com.osprey.math.exception.InsufficientHistoryException;
import com.osprey.screen.HotListItem;
import com.osprey.screen.repository.IHotShitRepository;
import com.osprey.screen.repository.IOspreyJSONObjectRepository;
import com.osprey.screen.repository.jdbctemplate.HotShitJdbcRepository;
import com.osprey.screen.repository.jdbctemplate.OspreyJSONObjectJdbcRepository;
import com.osprey.screen.service.BlackListService;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;
import com.osprey.securitymaster.repository.jdbctemplate.SecurityMasterJdbcRepository;

@Configuration
@EnableBatchProcessing
public class NightlyMarketDataScreen {

	private final static Logger logger = LogManager.getLogger(NightlyMarketDataScreen.class);

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

	// Queues to temporarily hold securities to quote and pull history for.
	private final ConcurrentLinkedQueue<SecurityQuoteContainer> postInitialScreenResultQueue = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<SecurityQuoteContainer> postQuoteQueue = new ConcurrentLinkedQueue<>();
	private final ConcurrentLinkedQueue<SecurityQuoteContainer> historicalModelQueue = new ConcurrentLinkedQueue<>();

	@Bean
	public NasdaqSecurityMasterFtpService nasdaqSecurityMasterFtpService() {
		return new NasdaqSecurityMasterFtpService(marketDataLoadDateService());
	}

	@Bean
	public YahooHistoricalQuoteClient yahooHistoricalQuoteClient() {
		return new YahooHistoricalQuoteClient();
	}

	@Bean
	public YahooQuoteClient yahooQuoteClient() {
		return new YahooQuoteClient(http);
	}

	@Bean
	@Scope("prototype")
	public YahooQuoteUrlBuilder yahooQuoteUrlBuilder(String symbol) {
		return new YahooQuoteUrlBuilder(symbol);
	}

	@Bean
	@Scope("prototype")
	public YahooHistoricalUrlBuilder yahooHistoricalUrlBuilder(String symbol, LocalDate start, LocalDate end,
			QuoteDataFrequency freq) {
		return new YahooHistoricalUrlBuilder(symbol, start, end, freq);
	}

	@Bean
	public MarketDataLoadDateService marketDataLoadDateService() {
		return new MarketDataLoadDateService(ospreyJSONObjectRepository(), om1);
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10);// TODO extract
		executor.setMaxPoolSize(16); // TODO extract
		executor.setThreadFactory(threadFactory());
		executor.setAllowCoreThreadTimeOut(false);
		executor.setKeepAliveSeconds(30); // TODO extract
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
	public MarketScheduleService marketScheduleService() {
		return new MarketScheduleService();
	}

	@Bean
	public IOspreyJSONObjectRepository ospreyJSONObjectRepository() {
		return new OspreyJSONObjectJdbcRepository(dataSource);
	}

	@Bean
	public IHotShitRepository hotShitRepository() {
		return new HotShitJdbcRepository(dataSource, om1);
	}

	@Bean
	public ISecurityMasterRepository securityMasterRepository() {
		return new SecurityMasterJdbcRepository(dataSource);
	}

	@Bean
	public SecurityMasterItemReader externalSecurityMasterReader() {
		return new SecurityMasterItemReader(nasdaqSecurityMasterFtpService(), securityMasterRepository(), slack);
	}

	@Bean
	QuoteItemReader quoteItemReader() {
		return new QuoteItemReader(securityMasterRepository(), executorService(), initialScreenService());
	}

	@Bean
	public HotShitScreenProvidor hotShitScreenProvidor() {
		return new HotShitScreenProvidor(this.ospreyJSONObjectRepository(), om1);
	}

	@Bean
	public BlackListService blackListService() {
		return new BlackListService(om1, ospreyJSONObjectRepository());
	}

	@Bean
	public InitialScreenService initialScreenService() {
		return new InitialScreenService(blackListService(), om1);
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
	public ItemReader<SecurityQuoteContainer> historicalQuoteQueueReader() {
		return new ItemReader<SecurityQuoteContainer>() {

			@Override
			public SecurityQuoteContainer read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

				return historicalModelQueue.poll();
			}

		};
	}

	@Bean
	public QuoteProcessor quoteProcessor() {
		return new QuoteProcessor(yahooQuoteClient(), yahooHistoricalQuoteClient(), marketScheduleService(),
				throttleCapacity());
	}

	@Bean
	public HotShitScreenProcessor hotShitScreenProcessor() {
		return new HotShitScreenProcessor(hotShitScreenProvidor(), initialScreenService());
	}

	@Bean
	public HistoricalModelProcessor historicalModelProcessor() {
		return new HistoricalModelProcessor(hotShitScreenProvidor(), hotShitRepository());
	}

	@Bean
	public PurgePreviousRunHotlistTasklet purgePreviousRunHotlistTasklet() {
		return new PurgePreviousRunHotlistTasklet(hotShitRepository());
	}

	@Bean
	public MarketDataLoadDecider marketDataLoadDecider() {
		return new MarketDataLoadDecider(marketDataLoadDateService(), marketScheduleService());
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
	public ItemWriter<SecurityQuoteContainer> postHistoricalModelItemWriter() {
		return new ItemWriter<SecurityQuoteContainer>() {

			@Override
			public void write(List<? extends SecurityQuoteContainer> items) throws Exception {
				postQuoteQueue.addAll(items);
			}

		};
	}

	@Bean
	public ItemWriter<SecurityQuoteContainer> postQuoteLoadQueueWriter() {
		return new ItemWriter<SecurityQuoteContainer>() {

			@Override
			public void write(List<? extends SecurityQuoteContainer> items) throws Exception {
				logger.debug("Queuing securities for the hot shit ...");
				historicalModelQueue.addAll(items);
			}

		};
	}

	@Bean
	public HotShitDbItemWriter hotShitDbItemWriter() {
		return new HotShitDbItemWriter(hotShitRepository());
	}

	@Bean
	QuoteContainerItemWriter quoteContainerItemWriter() {
		return new QuoteContainerItemWriter(postQuoteQueue, securityMasterRepository());
	}

	@Bean
	public ExponentialBackOffPolicy exponentialBackOffPolicy() {
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(1000);
		return backOffPolicy;
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionNotificationListener(hotShitRepository(), slack);
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}

	@Bean
	@Scope("prototype")
	public Job processNightlySecurityMaster() {
		return jobBuilderFactory.get("nightlySecurityMasterProcess")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(noOp()) 
				.next(marketDataLoadDecider())
				.on(MarketDataLoadDecider.DO_FETCH)
					.to(disruptorStartup())
					.next(quoteAndCalcAndPersist())
					.next(disruptorShutdown())
					.next(deletePreviousHotlistForToday())
					.next(hotListFilterAndPersist())
				.from(marketDataLoadDecider())
				.on(MarketDataLoadDecider.DO_LOAD)
					.to(loadQuotes())
					.next(historicalModelRuns())
					.next(deletePreviousHotlistForToday())
					.next(hotListFilterAndPersist())
				.end()
				.build();
	}

	@Bean
	public Step loadQuotes() {
		return stepBuilderFactory.get("loadSecurities")
				.allowStartIfComplete(true)
				.<SecurityQuoteContainer, SecurityQuoteContainer>chunk(250)
				.reader(quoteItemReader())
				.writer(postQuoteLoadQueueWriter())
				.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	public Step noOp() {
		return stepBuilderFactory.get("noop")
				.allowStartIfComplete(true)
				.tasklet(new NoOpTasklet())
				.build();
	}

	@Bean
	public Step quoteAndCalcAndPersist() {
		return stepBuilderFactory.get("quoteAndCalc")
				.allowStartIfComplete(true)
				.<SecurityQuoteContainer, SecurityQuoteContainer>chunk(8)
				.reader(externalSecurityMasterReader())
				.faultTolerant().backOffPolicy(exponentialBackOffPolicy())
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

	@Bean
	public Step deletePreviousHotlistForToday() {
		return stepBuilderFactory.get("hotListDestroyer")
				.allowStartIfComplete(true)
				.tasklet(purgePreviousRunHotlistTasklet())
				.build();
	}

	@Bean
	public Step hotListFilterAndPersist() {
		return stepBuilderFactory.get("hotListFilter").allowStartIfComplete(true)
				.<SecurityQuoteContainer, HotListItem>chunk(100)
				.faultTolerant()
				.skip(InsufficientHistoryException.class)
				.skipLimit(512)
				.reader(postQuoteQueueReader())
				.processor(hotShitScreenProcessor())
				.writer(hotShitDbItemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}

	@Bean
	public Step historicalModelRuns() {
		return stepBuilderFactory.get("historicalModelRuns").allowStartIfComplete(true)
				.<SecurityQuoteContainer, SecurityQuoteContainer>chunk(100)
				.faultTolerant()
				.skip(InsufficientHistoryException.class)
				.skipLimit(512)
				.reader(historicalQuoteQueueReader())
				.processor(historicalModelProcessor())
				.writer(postHistoricalModelItemWriter())
				.taskExecutor(taskExecutor())
				.build();
	}

}
