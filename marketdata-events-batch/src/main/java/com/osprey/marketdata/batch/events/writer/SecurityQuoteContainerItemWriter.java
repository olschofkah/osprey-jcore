package com.osprey.marketdata.batch.events.writer;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.repository.ISecurityMasterRepository;

public class SecurityQuoteContainerItemWriter implements ItemWriter<SecurityQuoteContainer> {

	final static Logger logger = LogManager.getLogger(SecurityQuoteContainerItemWriter.class);

	private ISecurityMasterRepository repo;

	public SecurityQuoteContainerItemWriter(ISecurityMasterRepository repo) {
		this.repo = repo;
	}

	@Override
	public void write(final List<? extends SecurityQuoteContainer> items) throws Exception {

		if (items.isEmpty()) {
			return;
		}

		for (SecurityQuoteContainer sqc : items) {
			logger.info("Persisting {} quote ... ", () -> sqc.getKey().getSymbol());
			repo.persistEventsAndFundamentals(sqc);
		}
	}

}
