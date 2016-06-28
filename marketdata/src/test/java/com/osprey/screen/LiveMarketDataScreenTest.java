package com.osprey.screen;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osprey.marketdata.MarketdataApplication;
import com.osprey.marketdata.feed.constants.QuoteDataFrequency;
import com.osprey.marketdata.feed.yahoo.YahooHistoricalQuoteClient;
import com.osprey.marketdata.feed.yahoo.YahooQuoteClient;
import com.osprey.screen.criteria.CrossDirection;
import com.osprey.screen.criteria.ExponentialMovingAverageCrossoverCriteria;
import com.osprey.screen.criteria.IStockScreenCriteria;
import com.osprey.screen.criteria.PreviousClosePriceCriteria;
import com.osprey.screen.criteria.RelationalOperator;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MarketdataApplication.class)
public class LiveMarketDataScreenTest {

	@Autowired
	private YahooQuoteClient yahooQuoteClient;
	@Autowired
	private YahooHistoricalQuoteClient yahooHistoricalQuoteClient;

	// TODO Add more test cases

	@Test
	public void quoteHistoricalTest1() throws Exception {

		LocalDate end = LocalDate.now();
		LocalDate start = end.minusYears(1).minusDays(10);
		QuoteDataFrequency freq = QuoteDataFrequency.DAY;

		String symbol = "IMPV";

		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));
		List<HistoricalQuote> hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start,
				end, freq);
		sqc.setHistoricalQuotes(hist);

		ExponentialMovingAverageCrossoverCriteria c1 = new ExponentialMovingAverageCrossoverCriteria(15, 50, 5,
				0, CrossDirection.FROM_BELOW_TO_ABOVE);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Set<SecurityQuoteContainer> securities = new HashSet<>();
		securities.add(sqc);

		ScreenPlanFactory factory = new ScreenPlanFactory();
		factory.setSecurityUniverse(securities);

		List<ScreenPlan> plans = factory.build(criteria);

		BasicScreenExecutor executor = new BasicScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<SecurityKey> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(sqc.getKey()));

	}
}
