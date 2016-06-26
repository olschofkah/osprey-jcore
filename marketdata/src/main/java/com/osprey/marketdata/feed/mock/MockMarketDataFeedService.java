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

import com.osprey.marketdata.feed.IUltraSecurityQuoteService;
import com.osprey.marketdata.feed.IHistoricalQuoteSerice;
import com.osprey.marketdata.feed.ILiveSecurityQuoteService;
import com.osprey.marketdata.feed.constants.QuoteDataFrequency;
import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.math.OspreyJavaMath;
import com.osprey.securitymaster.FundamentalQuote;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class MockMarketDataFeedService
		implements ILiveSecurityQuoteService, IHistoricalQuoteSerice, IUltraSecurityQuoteService {

	final static Logger logger = LogManager.getLogger(MockMarketDataFeedService.class);

	@Override
	public SecurityQuoteContainer quoteUltra(SecurityKey s) {
		logger.warn("Mock Quoting Fundamental for ticker {}", () -> s);
		return generateRandomUltraQuote(s);
	}

	@Override
	public SecurityQuoteContainer quoteUltra(Security s) throws MarketDataNotAvailableException, MarketDataIOException {
		logger.warn("Mock Quoting Fundamental for ticker {}", () -> s);
		SecurityQuoteContainer sqc = generateRandomUltraQuote(s.getKey());
		sqc.setSecurity(s);

		return sqc;
	}
	
	@Override
	public SecurityQuoteContainer quoteUltra(SecurityQuoteContainer sqc) throws MarketDataNotAvailableException, MarketDataIOException {
		logger.warn("Mock Quoting Fundamental for ticker {}", () -> sqc);
		return generateRandomUltraQuote(sqc);
	}

	@Override
	public List<HistoricalQuote> quoteHistorical(SecurityKey s, LocalDate start, LocalDate end,
			QuoteDataFrequency frequency) {
		logger.warn("Mock Historical Quote for ticker {}", () -> s);

		List<HistoricalQuote> result = generateHistoricalQuotes(s, start, end);
		return result;
	}

	@Override
	public Map<SecurityKey, List<HistoricalQuote>> quoteHistoricalBatch(Set<SecurityKey> s, LocalDate start,
			LocalDate end) {
		logger.warn("Mock Batch Historical Quote for ticker {}", () -> s);

		Map<SecurityKey, List<HistoricalQuote>> resultMap = new HashMap<>(OspreyJavaMath.calcMapInitialSize(s.size()));
		for (SecurityKey security : s) {
			resultMap.put(security, generateHistoricalQuotes(security, start, end));
		}

		return resultMap;
	}

	@Override
	public SecurityQuote quote(SecurityKey s) {
		logger.warn("Mock Quoting for ticker {}", () -> s);

		return generateRandomQuote(s);
	}

	@Override
	public Map<SecurityKey, SecurityQuote> quoteBatch(Set<SecurityKey> securities) {
		logger.warn("Mock Batch Quoting for tickers {}", () -> securities);

		Map<SecurityKey, SecurityQuote> resultMap = new HashMap<>(OspreyJavaMath.calcMapInitialSize(securities.size()));
		for (SecurityKey security : securities) {
			resultMap.put(security, generateRandomQuote(security));
		}

		return resultMap;
	}

	private SecurityQuoteContainer generateRandomUltraQuote(SecurityKey s) {
		SecurityQuoteContainer sqc = new SecurityQuoteContainer(s);
		return generateRandomUltraQuote(sqc);
	}
	
	private SecurityQuoteContainer generateRandomUltraQuote(SecurityQuoteContainer sqc) {

		FundamentalQuote fundamentalQuote = new FundamentalQuote(sqc.getKey(), LocalDate.now(), ZonedDateTime.now());
		SecurityQuote quote = generateRandomQuote(sqc.getKey());

		sqc.setFundamentalQuote(fundamentalQuote);
		sqc.setSecurityQuote(quote);

		// TODO add missing fundamentals

		fundamentalQuote.set_52WeekHigh(quote.getLast() + RandomUtils.nextDouble(5, 100));

		// fundamentalQuote.set_52High(quote.getLast() +
		// RandomUtils.nextDouble(5, 100));
		// fundamentalQuote.set_52Low(quote.getLast() -
		// RandomUtils.nextDouble(1, 4));
		// fundamentalQuote.setAnnualYield(RandomUtils.nextDouble(0, 0.05));
		// fundamentalQuote.setAnnualDividend(fundamentalQuote.getAnnualYield()
		// * 50);
		// fundamentalQuote.setBeta(RandomUtils.nextDouble(0, 4));
		// fundamentalQuote.setDayHigh(fundamentalQuote.getPrice() +
		// RandomUtils.nextDouble(0, 10));
		// fundamentalQuote.setDayLow(fundamentalQuote.getPrice() -
		// RandomUtils.nextDouble(1, 4));
		// fundamentalQuote.setEps(RandomUtils.nextDouble(0, 50));
		// fundamentalQuote.setHistoricalVolatility(RandomUtils.nextDouble(0.1,
		// 0.9));
		// fundamentalQuote.setMarketCap(RandomUtils.nextDouble(1000000,
		// 10000000000l));
		// fundamentalQuote.setNextDivDate(LocalDate.now().plusDays(RandomUtils.nextInt(0,
		// 75)));
		// fundamentalQuote.setNextEarningsDateLower(LocalDate.now().plusDays(RandomUtils.nextInt(0,
		// 75)));
		// fundamentalQuote.setPctHeldByInst(RandomUtils.nextDouble(0.01, 1.2));
		// fundamentalQuote.setPeRatio(RandomUtils.nextDouble(1, 200));
		// fundamentalQuote.setSharesOutstanding((long)
		// (fundamentalQuote.getMarketCap() / fundamentalQuote.getClose()));
		// fundamentalQuote.setShortInt(RandomUtils.nextDouble(1, 20));

		return sqc;
	}

	private SecurityQuote generateRandomQuote(SecurityKey s) {
		SecurityQuote ps = new SecurityQuote(s);
		ps.setTimestamp(ZonedDateTime.now());

		double randoDouble = RandomUtils.nextDouble(5, 120);

		ps.setAsk(randoDouble + RandomUtils.nextDouble(0.01, 0.50));
		ps.setBid(randoDouble - RandomUtils.nextDouble(0.01, 0.50));
		ps.setClose(randoDouble - RandomUtils.nextDouble(1, 3));
		ps.setLast(randoDouble);
		ps.setOpen(randoDouble + RandomUtils.nextDouble(1, 3));

		ps.setVolume(RandomUtils.nextInt(1000, 50000000));

		return ps;
	}

	private List<HistoricalQuote> generateHistoricalQuotes(SecurityKey s, LocalDate start, LocalDate end) {

		LocalDate day = start;

		List<HistoricalQuote> hist = new ArrayList<>();

		HistoricalQuote previousDay = null;
		while (day.isAfter(end)) {

			double seed;
			if (previousDay == null) {
				seed = RandomUtils.nextDouble(5, 120);
			} else {
				seed = previousDay.getClose();
			}

			HistoricalQuote sec = new HistoricalQuote(s.getSymbol(), day);
			sec.setAdjClose(seed + RandomUtils.nextDouble(0.01, 5.50));
			sec.setClose(seed + RandomUtils.nextDouble(0.01, 5.50));
			sec.setHigh(seed + RandomUtils.nextDouble(0.01, 5.50));
			sec.setLow(seed - RandomUtils.nextDouble(0.01, 4.50));
			sec.setOpen(seed);
			sec.setVolume(RandomUtils.nextInt(1000, 50000000));

			sec.setTimestamp(ZonedDateTime.now());

			hist.add(sec);

			day = day.minusDays(1);
		}

		return hist;
	}

}
