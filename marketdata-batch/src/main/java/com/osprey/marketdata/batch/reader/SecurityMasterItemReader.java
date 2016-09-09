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

	private ISecurityMasterService securityMasterService;
	private ISecurityMasterRepository repo;
	private SlackClient slack;

	public SecurityMasterItemReader(ISecurityMasterService securityMasterService, ISecurityMasterRepository repo,
			SlackClient slack) {
		this.securityMasterService = securityMasterService;
		this.repo = repo;
		this.slack = slack;
	}

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

		// TODO consider moving this to execute in a thread pool since it's doing data loads. 
		for (Security security : externalList) {
			tmpContainer = new SecurityQuoteContainer(security.getKey());
			tmpContainer.setSecurity(security);
			
			// load events as they will not be fetched through normal process
			tmpContainer.setEvents(repo.findSecurityEvents(security.getKey()));
			
			swapQueue.add(tmpContainer);
		}
		return swapQueue;
	}

}
