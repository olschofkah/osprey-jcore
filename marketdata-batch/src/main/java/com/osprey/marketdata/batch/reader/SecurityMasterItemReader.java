package com.osprey.marketdata.batch.reader;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.marketdata.feed.ISecurityMasterService;
import com.osprey.securitymaster.Security;

public class SecurityMasterItemReader implements ItemReader<Security> {

	private ConcurrentLinkedQueue<Security> queue;
	private volatile ReentrantLock lock = new ReentrantLock();

	@Autowired
	private ISecurityMasterService securityMasterService;

	@Override
	public Security read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		if (queue == null) {
			lock.lock();
			try {
				if (queue == null) {
					queue = new ConcurrentLinkedQueue<>(securityMasterService.fetchSecurityMaster());
				}
			} finally {
				lock.unlock();
			}
		}

		return queue.poll();
	}

}
