package com.osprey.marketdata.batch.reader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.marketdata.batch.listener.JobCompletionNotificationListener;
import com.osprey.marketdata.batch.processor.InitialScreenService;
import com.osprey.math.OspreyJavaMath;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;

public class QuoteItemReader implements ItemReader<SecurityQuoteContainer> {

	final static Logger logger = LogManager.getLogger(JobCompletionNotificationListener.class);

	private ConcurrentLinkedQueue<SecurityQuoteContainer> queue;
	private volatile ReentrantLock lock = new ReentrantLock();

	@Autowired
	private ISecurityMasterRepository repo;
	@Autowired
	private ExecutorService executor;
	@Autowired
	private InitialScreenService initialScreenService;

	@Override
	public SecurityQuoteContainer read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		if (queue == null) {
			lock.lock();
			try {
				if (queue == null) {

					List<Security> securities = repo.findSecurities();

					Set<SecurityQuoteContainer> tempList = new HashSet<>(
							OspreyJavaMath.calcMapInitialSize(securities.size()));
					List<Future<SecurityQuoteContainer>> theFutures = new ArrayList<>(securities.size());

					Callable<SecurityQuoteContainer> callable;
					for (Security s : securities) {

						callable = new Callable<SecurityQuoteContainer>() {

							@Override
							public SecurityQuoteContainer call() throws Exception {
								logger.info("loading {} from the db ... ", () -> s.getKey().getSymbol());
								return repo.findSecurityQuoteContainer(s.getKey(), LocalDate.now().minusYears(3),
										LocalDate.now());
							}
						};

						theFutures.add(executor.submit(callable));
					}

					for (Future<SecurityQuoteContainer> future : theFutures) {
						tempList.add(future.get());
					}

					Set<SecurityKey> filteredSet = initialScreenService.filter(tempList);

					List<SecurityQuoteContainer> finalList = new ArrayList<>();
					for (SecurityQuoteContainer sqc : tempList) {
						if (filteredSet.contains(sqc.getKey())) {
							finalList.add(sqc);
							sqc.sortEventsDescending();
						}
					}

					queue = new ConcurrentLinkedQueue<>(finalList);
				}
			} finally {
				lock.unlock();
			}
		}

		return queue.poll();
	}

}
