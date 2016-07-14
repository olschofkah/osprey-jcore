package com.osprey.marketdata.batch.reader;

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
import com.osprey.securitymaster.repository.ISecurityMasterRepository;

public class SecurityMasterItemReader implements ItemReader<Security> {

	final static Logger logger = LogManager.getLogger(JobCompletionNotificationListener.class);

	private ConcurrentLinkedQueue<Security> queue;
	private volatile ReentrantLock lock = new ReentrantLock();

	@Autowired
	private ISecurityMasterService securityMasterService;

	@Autowired
	private ISecurityMasterRepository repo;

	@Autowired
	private SlackClient slack;

	@Override
	public Security read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		if (queue == null) {
			lock.lock();
			try {
				if (queue == null) {
					queue = new ConcurrentLinkedQueue<>(securityMasterService.fetchSecurityMaster());
				}
			} catch (MarketDataIOException e) {
				logger.error(e);

				slack.postMessage(
						"Failed to externally fetch the security master.  Loading from the previous day ... ");

				if (queue == null) {
					queue = new ConcurrentLinkedQueue<>(repo.findSecurities());
				}
			} finally {
				lock.unlock();
			}
		}

		return queue.poll();
	}

}
