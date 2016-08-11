package com.osprey.marketdata.batch.processor;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class InitialScreenProcessor implements ItemProcessor<Security, SecurityQuoteContainer> {

	final static Logger logger = LogManager.getLogger(InitialScreenProcessor.class);

	@Autowired
	private InitialScreenService initialScreenService;

	@Override
	public SecurityQuoteContainer process(Security security) throws Exception {

		logger.info("Performing initial screen on {} ", () -> security.getKey());

		SecurityQuoteContainer sqc = new SecurityQuoteContainer(security.getKey());
		sqc.setSecurity(security);

		Set<SecurityQuoteContainer> securities = new HashSet<>(2);
		securities.add(sqc);

		Set<SecurityKey> filteredSet = initialScreenService.filter(securities);

		if (filteredSet.contains(security.getKey())) {
			return sqc;
		} else {
			return null;
		}
	}

}
