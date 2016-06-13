package com.osprey.marketdata.feed.mock;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.osprey.marketdata.feed.ISecurityMasterService;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.constants.InstrumentType;

@Service
public class MockSecurityMasterService implements ISecurityMasterService {

	final static Logger logger = LogManager.getLogger(MockMarketDataFeedService.class);

	@Override
	public Set<Security> fetchSecurityMaster() {

		logger.warn("Mock Security Master Fetch was called. ");

		Set<Security> securities = new HashSet<>();
		securities.add(generateSecurity("GOOGL", InstrumentType.STOCK));
		securities.add(generateSecurity("AAPL", InstrumentType.STOCK));
		securities.add(generateSecurity("MSFT", InstrumentType.STOCK));
		securities.add(generateSecurity("GS", InstrumentType.STOCK));
		securities.add(generateSecurity("GM", InstrumentType.STOCK));
		securities.add(generateSecurity("JOY", InstrumentType.STOCK));
		securities.add(generateSecurity("KBH", InstrumentType.STOCK));
		securities.add(generateSecurity("NFLX", InstrumentType.STOCK));
		securities.add(generateSecurity("TSLA", InstrumentType.STOCK));
		securities.add(generateSecurity("FB", InstrumentType.STOCK));
		securities.add(generateSecurity("AMZN", InstrumentType.STOCK));
		securities.add(generateSecurity("QQQ", InstrumentType.ETF));
		securities.add(generateSecurity("XIB", InstrumentType.ETF));
		securities.add(generateSecurity("XSD", InstrumentType.ETF));

		return securities;
	}

	private Security generateSecurity(String ticker, InstrumentType type) {
		Security securtiy = new Security(ticker);
		securtiy.setInstrumentType(type);
		securtiy.setTimestamp(ZonedDateTime.now());
		return securtiy;
	}

}
