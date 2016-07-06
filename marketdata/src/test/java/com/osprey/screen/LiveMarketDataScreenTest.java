package com.osprey.screen;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.osprey.math.OspreyQuantMath;
import com.osprey.screen.criteria.BetaCriteria;
import com.osprey.screen.criteria.EarningsCriteria;
import com.osprey.screen.criteria.ExponentialMovingAverageBandCrossoverCriteria;
import com.osprey.screen.criteria.ExponentialMovingAverageCriteria;
import com.osprey.screen.criteria.ExponentialMovingAverageCrossoverCriteria;
import com.osprey.screen.criteria.ExponentialMovingAverageCurrentPriceCrossoverCriteria;
import com.osprey.screen.criteria.ExponentialMovingAverageVsCurrentPriceCriteria;
import com.osprey.screen.criteria.IScreenCriteria;
import com.osprey.screen.criteria.InstrumentTypeCriteria;
import com.osprey.screen.criteria.MovingAverageConverganceDiverganceCrossoverCriteria;
import com.osprey.screen.criteria.PreviousClosePriceCriteria;
import com.osprey.screen.criteria.PriceGapCriteria;
import com.osprey.screen.criteria.PricePercentageChangeCriteria;
import com.osprey.screen.criteria.RsiCriteria;
import com.osprey.screen.criteria.SimpleMovingAverageCriteria;
import com.osprey.screen.criteria.SymbolCriteria;
import com.osprey.screen.criteria.VolatilityCriteria;
import com.osprey.screen.criteria.VolumeAverageComparisonCriteria;
import com.osprey.screen.criteria.VolumeAverageCriteria;
import com.osprey.screen.criteria._52WeekRangePercentageCriteria;
import com.osprey.screen.criteria.constants.BandSelection;
import com.osprey.screen.criteria.constants.CrossDirection;
import com.osprey.screen.criteria.constants.GapDirection;
import com.osprey.screen.criteria.constants.RelationalOperator;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.InstrumentType;

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

		String symbol = "AMZN";

		Security security = new Security(new SecurityKey(symbol, null));
		security.setInstrumentType(InstrumentType.STOCK);

		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));
		List<HistoricalQuote> hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start,
				end, freq);
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);

		_52WeekRangePercentageCriteria c1 = new _52WeekRangePercentageCriteria(.2, RelationalOperator._GT);

		BetaCriteria c2 = new BetaCriteria(1, RelationalOperator._GE);

		EarningsCriteria c3 = new EarningsCriteria(30, RelationalOperator._LE);

		ExponentialMovingAverageBandCrossoverCriteria c4 = new ExponentialMovingAverageBandCrossoverCriteria(20, 3,
				0.03, CrossDirection.FROM_BELOW_TO_ABOVE, BandSelection.LOWER_BAND);

		ExponentialMovingAverageCriteria c5 = new ExponentialMovingAverageCriteria(4, 9, RelationalOperator._LT);

		ExponentialMovingAverageCrossoverCriteria c6 = new ExponentialMovingAverageCrossoverCriteria(15, 50, 5,
				CrossDirection.FROM_BELOW_TO_ABOVE);

		ExponentialMovingAverageCurrentPriceCrossoverCriteria c7 = new ExponentialMovingAverageCurrentPriceCrossoverCriteria(
				15, 5, CrossDirection.FROM_BELOW_TO_ABOVE);

		InstrumentTypeCriteria c8 = new InstrumentTypeCriteria(
				new HashSet<InstrumentType>(Arrays.asList(new InstrumentType[] { InstrumentType.STOCK })), false);

		// MomentumScreenCriteria c9 = new MomentumScreenCriteria(15, 50, 5, 0,
		// CrossDirection.FROM_BELOW_TO_ABOVE);

		PreviousClosePriceCriteria c10 = new PreviousClosePriceCriteria(5, RelationalOperator._GE);

		PriceGapCriteria c11 = new PriceGapCriteria(.03, GapDirection.GAP_UP, RelationalOperator._GE);

		PricePercentageChangeCriteria c12 = new PricePercentageChangeCriteria(.05, 45, RelationalOperator._GE);

		SimpleMovingAverageCriteria c13 = new SimpleMovingAverageCriteria(12, 26, RelationalOperator._GE);

		SymbolCriteria c14 = new SymbolCriteria(new HashSet<String>(Arrays.asList(new String[] { "SPY", "QQQ" })),
				false);

		VolatilityCriteria c15 = new VolatilityCriteria(23, 50, RelationalOperator._GE);

		VolumeAverageComparisonCriteria c16 = new VolumeAverageComparisonCriteria(12, 26, RelationalOperator._GE);

		VolumeAverageCriteria c17 = new VolumeAverageCriteria(1000000, 5, RelationalOperator._GE);

		RsiCriteria c18 = new RsiCriteria(252, 50, RelationalOperator._GE);

		ExponentialMovingAverageVsCurrentPriceCriteria c19 = new ExponentialMovingAverageVsCurrentPriceCriteria(15,
				RelationalOperator._GE);

		List<IScreenCriteria> cList = new ArrayList<>();
		cList.add(c1);
		cList.add(c2);
		cList.add(c3);
		cList.add(c4);
		cList.add(c5);
		cList.add(c6);
		cList.add(c7);
		cList.add(c8);
		// cList.add(c9);
		cList.add(c10);
		cList.add(c11);
		cList.add(c12);
		cList.add(c13);
		cList.add(c14);
		cList.add(c15);
		cList.add(c16);
		cList.add(c17);
		cList.add(c18);
		cList.add(c19);

		for (IScreenCriteria c : cList) {
			List<IScreenCriteria> criteria = new ArrayList<>();
			criteria.add(c);

			Set<SecurityQuoteContainer> securities = new HashSet<>();
			securities.add(sqc);

			ScreenPlanFactory factory = new ScreenPlanFactory();
			factory.setSecurityUniverse(securities);

			List<ScreenPlan> plans = factory.build(criteria);

			SimpleScreenExecutor executor = new SimpleScreenExecutor();
			executor.setPlans(plans);
			executor.execute();

			Set<SecurityKey> resultSet = executor.getResultSet();

			// Assert.assertTrue(resultSet.contains(sqc.getKey())); ZHNE
		}

	}

	@Test
	public void calcVolatility() throws Exception {

		LocalDate end = LocalDate.now();
		LocalDate start = end.minusYears(1).minusDays(10);
		QuoteDataFrequency freq = QuoteDataFrequency.DAY;

		String symbol = "SPY";

		Security security = new Security(new SecurityKey(symbol, null));
		security.setInstrumentType(InstrumentType.STOCK);

		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));
		List<HistoricalQuote> hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start,
				end, freq);
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);

		double volatility = OspreyQuantMath.volatility(252, hist);
		System.out.println(volatility);
	}

	@Test
	public void earningsBetaTest() throws Exception {

		LocalDate end = LocalDate.now();
		LocalDate start = end.minusYears(1).minusDays(10);
		QuoteDataFrequency freq = QuoteDataFrequency.DAY;

		String symbol = "ZHNE";

		Security security = new Security(new SecurityKey(symbol, null));
		security.setInstrumentType(InstrumentType.STOCK);

		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));
		List<HistoricalQuote> hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start,
				end, freq);
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);

		BetaCriteria c2 = new BetaCriteria(2, RelationalOperator._GE);

		EarningsCriteria c3 = new EarningsCriteria(30, RelationalOperator._LE);

		List<IScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c2);
		criteria.add(c3);

		Set<SecurityQuoteContainer> securities = new HashSet<>();
		securities.add(sqc);

		ScreenPlanFactory factory = new ScreenPlanFactory();
		factory.setSecurityUniverse(securities);

		List<ScreenPlan> plans = factory.build(criteria);

		SimpleScreenExecutor executor = new SimpleScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<SecurityKey> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(sqc.getKey()));

	}

	@Test
	public void volatilityTest() throws Exception {

		LocalDate end = LocalDate.now();
		LocalDate start = end.minusYears(1).minusDays(10);
		QuoteDataFrequency freq = QuoteDataFrequency.DAY;

		String symbol = "NFLX";

		Security security = new Security(new SecurityKey(symbol, null));
		security.setInstrumentType(InstrumentType.STOCK);

		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));
		List<HistoricalQuote> hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start,
				end, freq);
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);

		_52WeekRangePercentageCriteria c1 = new _52WeekRangePercentageCriteria(.2, RelationalOperator._GT);

		BetaCriteria c2 = new BetaCriteria(2, RelationalOperator._GE);

		EarningsCriteria c3 = new EarningsCriteria(30, RelationalOperator._LE);

		// ExponentialMovingAverageBandCrossoverCriteria c4 = new
		// ExponentialMovingAverageBandCrossoverCriteria(15, 50, 5,
		// 0, CrossDirection.FROM_BELOW_TO_ABOVE);

		ExponentialMovingAverageCriteria c5 = new ExponentialMovingAverageCriteria(4, 9, RelationalOperator._LT);

		ExponentialMovingAverageCrossoverCriteria c6 = new ExponentialMovingAverageCrossoverCriteria(15, 50, 5,
				CrossDirection.FROM_BELOW_TO_ABOVE);

		ExponentialMovingAverageCurrentPriceCrossoverCriteria c7 = new ExponentialMovingAverageCurrentPriceCrossoverCriteria(
				15, 5, CrossDirection.FROM_BELOW_TO_ABOVE);

		InstrumentTypeCriteria c8 = new InstrumentTypeCriteria(
				new HashSet<InstrumentType>(Arrays.asList(new InstrumentType[] { InstrumentType.STOCK })), false);

		// MomentumScreenCriteria c9 = new MomentumScreenCriteria(15, 50, 5, 0,
		// CrossDirection.FROM_BELOW_TO_ABOVE);

		PreviousClosePriceCriteria c10 = new PreviousClosePriceCriteria(5, RelationalOperator._GE);

		PriceGapCriteria c11 = new PriceGapCriteria(.03, GapDirection.GAP_UP, RelationalOperator._GE);

		PricePercentageChangeCriteria c12 = new PricePercentageChangeCriteria(.05, 45, RelationalOperator._GE);

		SimpleMovingAverageCriteria c13 = new SimpleMovingAverageCriteria(12, 26, RelationalOperator._GE);

		SymbolCriteria c14 = new SymbolCriteria(new HashSet<String>(Arrays.asList(new String[] { "SPY", "QQQ" })),
				false);

		VolatilityCriteria c15 = new VolatilityCriteria(252, 20, RelationalOperator._GE);

		VolumeAverageComparisonCriteria c16 = new VolumeAverageComparisonCriteria(12, 26, RelationalOperator._GE);

		VolumeAverageCriteria c17 = new VolumeAverageCriteria(1000000, 5, RelationalOperator._GE);

		List<IScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c15);

		Set<SecurityQuoteContainer> securities = new HashSet<>();
		securities.add(sqc);

		ScreenPlanFactory factory = new ScreenPlanFactory();
		factory.setSecurityUniverse(securities);

		List<ScreenPlan> plans = factory.build(criteria);

		SimpleScreenExecutor executor = new SimpleScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<SecurityKey> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(sqc.getKey()));

	}
	

	@Test
	public void macdTest() throws Exception {

		LocalDate end = LocalDate.now();
		LocalDate start = end.minusYears(1).minusDays(10);
		QuoteDataFrequency freq = QuoteDataFrequency.DAY;

		String symbol = "QQQ";

		Security security = new Security(new SecurityKey(symbol, null));
		security.setInstrumentType(InstrumentType.STOCK);

		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));
		List<HistoricalQuote> hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start,
				end, freq);
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);

		IScreenCriteria c1 = new MovingAverageConverganceDiverganceCrossoverCriteria(12, 26, 9, 10, CrossDirection.FROM_BELOW_TO_ABOVE);

		List<IScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Set<SecurityQuoteContainer> securities = new HashSet<>();
		securities.add(sqc);

		ScreenPlanFactory factory = new ScreenPlanFactory();
		factory.setSecurityUniverse(securities);

		List<ScreenPlan> plans = factory.build(criteria);

		SimpleScreenExecutor executor = new SimpleScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<SecurityKey> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(sqc.getKey()));

	}


}
