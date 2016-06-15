package com.osprey.marketdata.feed.mock;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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
import com.osprey.securitymaster.constants.EarningsReportTime;

@Service
public class MockMarketDataFeedService
		implements ILiveSecurityQuoteService, IHistoricalSecurityQuoteSerice, IFundamentalSecurityQuoteService {

	final static Logger logger = LogManager.getLogger(MockMarketDataFeedService.class);

	@Override
	public FundamentalPricedSecurity quoteFundamental(Security s) {
		logger.warn("Mock Quoting Fundamental for ticker {}", () -> s);
		return generateRandomFundamentalPricedSecurity(s);
	}

	@Override
	public Map<Security, FundamentalPricedSecurity> quoteFundamentalBatch(Set<Security> s) {
		logger.warn("Mock Quoting Fundamental for tickers {}", () -> s);

		Map<Security, FundamentalPricedSecurity> resultMap = new HashMap<>(OspreyJavaMath.calcMapInitialSize(s.size()));
		for (Security security : s) {
			resultMap.put(security, generateRandomFundamentalPricedSecurity(security));
		}

		return resultMap;
	}

	@Override
	public List<HistoricalSecurity> fetchHistorical(Security s, ZonedDateTime start, ZonedDateTime end) {
		logger.warn("Mock Historical Quote for ticker {}", () -> s);

		List<HistoricalSecurity> result = generateHistoricalQuotes(s, start, end);
		return result;
	}

	@Override
	public Map<Security, List<HistoricalSecurity>> fetchHistoricalBatch(Set<Security> s, ZonedDateTime start,
			ZonedDateTime end) {
		logger.warn("Mock Batch Historical Quote for ticker {}", () -> s);

		Map<Security, List<HistoricalSecurity>> resultMap = new HashMap<>(OspreyJavaMath.calcMapInitialSize(s.size()));
		for (Security security : s) {
			resultMap.put(security, generateHistoricalQuotes(security, start, end));
		}

		return resultMap;
	}

	@Override
	public PricedSecurity quote(Security s) {
		logger.warn("Mock Quoting for ticker {}", () -> s);

		return generateRandomPricedSecurity(s);
	}

	@Override
	public Map<Security, PricedSecurity> quoteBatch(Set<Security> securities) {
		logger.warn("Mock Batch Quoting for tickers {}", () -> securities);

		Map<Security, PricedSecurity> resultMap = new HashMap<>(OspreyJavaMath.calcMapInitialSize(securities.size()));
		for (Security security : securities) {
			resultMap.put(security, generateRandomPricedSecurity(security));
		}

		return resultMap;
	}

	private FundamentalPricedSecurity generateRandomFundamentalPricedSecurity(Security s) {

		PricedSecurity quote = generateRandomPricedSecurity(s);
		FundamentalPricedSecurity fundamentalQuote = new FundamentalPricedSecurity(quote);

		fundamentalQuote.set_52High(fundamentalQuote.getLastPrice() + RandomUtils.nextDouble(5, 100));
		fundamentalQuote.set_52Low(fundamentalQuote.getLastPrice() - RandomUtils.nextDouble(1, 4));
		fundamentalQuote.setAnnualYield(RandomUtils.nextDouble(0, 0.05));
		fundamentalQuote.setAnnualDividend(fundamentalQuote.getAnnualYield() * 50);
		fundamentalQuote.setBeta(RandomUtils.nextDouble(0, 4));
		fundamentalQuote.setDayHigh(fundamentalQuote.getLastPrice() + RandomUtils.nextDouble(0, 10));
		fundamentalQuote.setDayLow(fundamentalQuote.getLastPrice() - RandomUtils.nextDouble(1, 4));
		fundamentalQuote.setEps(RandomUtils.nextDouble(0, 50));
		fundamentalQuote.setHistoricalVolatility(RandomUtils.nextDouble(0.1, 0.9));
		fundamentalQuote.setMarketCap(RandomUtils.nextDouble(1000000, 10000000000l));
		fundamentalQuote.setNextDivDate(LocalDate.now().plusDays(RandomUtils.nextInt(0, 75)));
		fundamentalQuote.setNextEarningsDate(LocalDate.now().plusDays(RandomUtils.nextInt(0, 75)));
		fundamentalQuote.setNextEarningsReportTime(EarningsReportTime.PRE_MARKET);
		fundamentalQuote.setPctHeldByInst(RandomUtils.nextDouble(0.01, 1.2));
		fundamentalQuote.setPeRatio(RandomUtils.nextDouble(1, 200));
		fundamentalQuote.setSharesOutstanding((long) (fundamentalQuote.getMarketCap() / fundamentalQuote.getClose()));
		fundamentalQuote.setShortInt(RandomUtils.nextDouble(1, 20));

		return fundamentalQuote;
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

	private List<HistoricalSecurity> generateHistoricalQuotes(Security s, ZonedDateTime start, ZonedDateTime end) {

		ZonedDateTime day = start;

		List<HistoricalSecurity> hist = new ArrayList<>();

		HistoricalSecurity previousDay = null;
		while (day.isAfter(end)) {

			double seed;
			if (previousDay == null) {
				seed = RandomUtils.nextDouble(5, 120);
			} else {
				seed = previousDay.getClose();
			}

			HistoricalSecurity sec = new HistoricalSecurity(s.getTicker(), day);
			sec.setAdjClose(seed + RandomUtils.nextDouble(0.01, 5.50));
			sec.setClose(seed + RandomUtils.nextDouble(0.01, 5.50));
			sec.setHigh(seed + RandomUtils.nextDouble(0.01, 5.50));
			sec.setLow(seed - RandomUtils.nextDouble(0.01, 4.50));
			sec.setOpen(seed);
			sec.setVolume(RandomUtils.nextInt(1000, 50000000));

			/**
			 * TODO ... Set next & previous div/earnings dates in a series
			 * 
			 * sec.setNextDivDate(ZonedDateTime.now().plusDays(RandomUtils.
			 * nextInt(0, 75)));
			 * sec.setNextEarningsDate(ZonedDateTime.now().plusDays(RandomUtils.
			 * nextInt(0, 75)));
			 * sec.setNextEarningsReportTime(EarningsReportTime.PRE_MARKET);
			 * 
			 * sec.setPreviousDivDate(previousDivDate);
			 * sec.setPreviousEarningsDate(previousEarningsDate);
			 * sec.setPreviousEarningsReportTime(previousEarningsReportTime);
			 * 
			 * 
			 */

			sec.setTimestamp(ZonedDateTime.now());
			
			hist.add(sec);
			
			day = day.minusDays(1);
		}

		return hist;
	}

}
