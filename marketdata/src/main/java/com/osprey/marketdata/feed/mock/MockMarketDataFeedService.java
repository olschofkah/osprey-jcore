package com.osprey.marketdata.feed.mock;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.osprey.marketdata.feed.IFundamentalSecurityQuoteService;
import com.osprey.marketdata.feed.IHistoricalSecurityQuoteSerice;
import com.osprey.marketdata.feed.ILiveSecurityQuoteService;
import com.osprey.math.OspreyJavaMath;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;
import com.osprey.securitymaster.PricedSecurity;
import com.osprey.securitymaster.Security;

@Service
public class MockMarketDataFeedService
		implements ILiveSecurityQuoteService, IHistoricalSecurityQuoteSerice, IFundamentalSecurityQuoteService {

	final static Logger logger = LogManager.getLogger(MockMarketDataFeedService.class);

	@Override
	public FundamentalPricedSecurity quoteFundamental(Set<Security> s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Security, FundamentalPricedSecurity> quoteFundamentalBatch(Set<Security> s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HistoricalSecurity> fetchHistorical(Security s, ZonedDateTime start, ZonedDateTime end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Security, List<HistoricalSecurity>> fetchHistoricalBatch(Set<Security> s, ZonedDateTime start,
			ZonedDateTime end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PricedSecurity quote(Security s) {
		logger.warn("Mock Quoting for ticker " + s);

		return generateRandomPricedSecurity(s);
	}

	@Override
	public Map<Security, PricedSecurity> quoteBatch(Set<Security> securities) {
		logger.warn("Mock Quoting for tickers " + securities);

		Map<Security, PricedSecurity> resultMap = new HashMap<>(OspreyJavaMath.calcMapInitialSize(securities.size()));
		for (Security security : securities) {
			resultMap.put(security, generateRandomPricedSecurity(security));
		}

		return resultMap;
	}

	private PricedSecurity generateRandomPricedSecurity(Security s) {
		PricedSecurity ps = new PricedSecurity(s);
		ps.setTimestamp(ZonedDateTime.now());

		double randoDouble = RandomUtils.nextDouble(5, 120);

		ps.setAsk(randoDouble + RandomUtils.nextDouble(0.01, 0.50));
		ps.setBid(randoDouble - RandomUtils.nextDouble(0.01, 0.50));
		ps.setClose(randoDouble - RandomUtils.nextDouble(1, 3));
		ps.setLastPrice(randoDouble);
		ps.setOpen(randoDouble + RandomUtils.nextDouble(1, 3));

		ps.setVolume(RandomUtils.nextInt(1000, 50000000));

		return ps;
	}

}
