package com.osprey.marketdata.batch.writer;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;

public class QuoteContainerItemWriter implements ItemWriter<SecurityQuoteContainer> {

	final static Logger logger = LogManager.getLogger(QuoteContainerItemWriter.class);

	private ISecurityMasterRepository repo;

	private ConcurrentLinkedQueue<SecurityQuoteContainer> postQuoteQueue;

	public QuoteContainerItemWriter(ConcurrentLinkedQueue<SecurityQuoteContainer> postQuoteQueue,
			ISecurityMasterRepository repo) {
		this.postQuoteQueue = postQuoteQueue;
		this.repo = repo;
	}

	@Override
	public void write(final List<? extends SecurityQuoteContainer> items) throws Exception {

		if (items.isEmpty()) {
			return;
		}

		for (SecurityQuoteContainer sqc : items) {
			logger.info("Persisting {} quote ... ", () -> sqc.getKey().getSymbol());
			repo.persist(sqc);
		}

		postQuoteQueue.addAll(items);
	}

}
