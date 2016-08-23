package com.osprey.marketdata.batch.reader;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.integration.slack.SlackClient;
import com.osprey.marketdata.batch.listener.JobCompletionNotificationListener;
import com.osprey.marketdata.feed.ISecurityMasterService;
import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;

public class SecurityMasterItemReader implements ItemReader<SecurityQuoteContainer> {

	final static Logger logger = LogManager.getLogger(JobCompletionNotificationListener.class);

	private ConcurrentLinkedQueue<SecurityQuoteContainer> queue;
	private volatile ReentrantLock lock = new ReentrantLock();

	@Autowired
	private ISecurityMasterService securityMasterService;

	@Autowired
	private ISecurityMasterRepository repo;

	@Autowired
	private SlackClient slack;

	@Override
	public SecurityQuoteContainer read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		if (queue == null) {
			lock.lock();
			try {
				if (queue == null) {
					Set<Security> externalList = securityMasterService.fetchSecurityMaster();
					queue = convertSecurityToContainer(externalList);
				}
			} catch (MarketDataIOException e) {
				logger.error(e);

				slack.postMessage(
						"Failed to externally fetch the security master.  Loading from the previous day ... ");

				if (queue == null) {
					List<Security> dbList = repo.findSecurities();
					queue = new ConcurrentLinkedQueue<>(convertSecurityToContainer(dbList));
				}
			} finally {
				lock.unlock();
			}
		}

		return queue.poll();
	}

	private ConcurrentLinkedQueue<SecurityQuoteContainer> convertSecurityToContainer(
			Collection<Security> externalList) {
		SecurityQuoteContainer tmpContainer;
		ConcurrentLinkedQueue<SecurityQuoteContainer> swapQueue = new ConcurrentLinkedQueue<>();

		for (Security security : externalList) {
			tmpContainer = new SecurityQuoteContainer(security.getKey());
			tmpContainer.setSecurity(security);
			swapQueue.add(tmpContainer);
		}
		return swapQueue;
	}

}
