package com.osprey.screen;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osprey.marketdata.MarketDataTestConfiguration;
import com.osprey.marketdata.feed.constants.QuoteDataFrequency;
import com.osprey.marketdata.feed.yahoo.YahooHistoricalQuoteClient;
import com.osprey.marketdata.feed.yahoo.YahooQuoteClient;
import com.osprey.math.EarningsCalculator;
import com.osprey.math.OspreyQuantMath;
import com.osprey.math.result.StochasticOscillatorCurve;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.InstrumentType;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("integration-test")
//@SpringApplicationConfiguration(classes = MarketdataApplication.class)
@SpringApplicationConfiguration(classes = MarketDataTestConfiguration.class)
public class LiveMarketDataCalcTest {

	@Autowired
	private YahooQuoteClient yahooQuoteClient;
	@Autowired
	private YahooHistoricalQuoteClient yahooHistoricalQuoteClient;

	@Test
	public void volatilityCalcTest1() throws Exception {

		LocalDate end = LocalDate.now();
		LocalDate start = end.minusYears(1).minusDays(10);
		QuoteDataFrequency freq = QuoteDataFrequency.DAY;

		String symbol = "UWTI";

		Security security = new Security(new SecurityKey(symbol, null));
		security.setInstrumentType(InstrumentType.STOCK);

		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));
		List<HistoricalQuote> hist = new ArrayList<>(
				yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start, end, freq));
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);

		double volatility = OspreyQuantMath.volatility(252, sqc.getHistoricalQuotes());

		System.out.println(volatility);
	}

	@Test
	public void emaCalcTest1() throws Exception {

		LocalDate end = LocalDate.now();
		LocalDate start = end.minusYears(1).minusDays(10);
		QuoteDataFrequency freq = QuoteDataFrequency.DAY;

		String symbol = "QQQ";

		Security security = new Security(new SecurityKey(symbol, null));
		security.setInstrumentType(InstrumentType.STOCK);

		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));
		List<HistoricalQuote> hist = new ArrayList<>(
				yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start, end, freq));
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);

		long currentTimeMillis = System.currentTimeMillis();
		double ema5 = OspreyQuantMath.ema(12, 0, hist);
		System.out.println(System.currentTimeMillis() - currentTimeMillis);

		//
		// System.out.println(ema1 + " close:" + hist.get(4));
		// System.out.println(ema2 + " close:" + hist.get(3));
		// System.out.println(ema3 + " close:" + hist.get(2));
		// System.out.println(ema4 + " close:" + hist.get(1));
		System.out.println(ema5 + " close:" + hist.get(0));
	}

	@Test
	public void rsiCalcTest1() throws Exception {

		LocalDate end = LocalDate.now();
		LocalDate start = end.minusYears(3).minusDays(10);
		QuoteDataFrequency freq = QuoteDataFrequency.DAY;

		String symbol = "QQQ";

		Security security = new Security(new SecurityKey(symbol, null));
		security.setInstrumentType(InstrumentType.STOCK);

		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));
		List<HistoricalQuote> hist = new ArrayList<>(
				yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start, end, freq));
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);

		System.out.println(OspreyQuantMath.rsiUsingSma(14, 0, hist));
		System.out.println(OspreyQuantMath.rsiUsingEma(14, 0, hist));
		System.out.println(OspreyQuantMath.rsiUsingWilders(14, 0, hist));
		System.out.println(OspreyQuantMath.wildersMovingAverage(14, 0, hist));
	}
	
	@Test
	public void earningsMovementCalc() throws Exception {

		LocalDate end = LocalDate.now();
		LocalDate start = end.minusYears(3).minusDays(10);
		QuoteDataFrequency freq = QuoteDataFrequency.DAY;

		String symbol = "CIEN";

		Security security = new Security(new SecurityKey(symbol, null));
		security.setInstrumentType(InstrumentType.STOCK);

		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));
		List<HistoricalQuote> hist = new ArrayList<>(
				yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start, end, freq));
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);
		
		sqc.sortEventsDescending();

		System.out.println(EarningsCalculator.calcEarningsPercentMove(sqc, 10, 4));
	}

	@Test
	public void momentumCurveTest1() throws Exception {

		// Determine the range of historical data to pull from yahoo
		LocalDate end = LocalDate.now(); // today
		LocalDate start = end.minusYears(3).minusDays(10); // 3 years & 10 days
															// ago
		QuoteDataFrequency freq = QuoteDataFrequency.DAY; // Frequency, daily

		// Define the symbol
		String symbol = "QQQ";

		// Construct the security object
		Security security = new Security(new SecurityKey(symbol, null));
		security.setInstrumentType(InstrumentType.STOCK);

		// Pull the fundamental and current quote from live yahoo for QQQ
		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));

		// Pull the historical quotes for the previously defined range from
		// yahoo for QQQ
		List<HistoricalQuote> hist = new ArrayList<>(
				yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start, end, freq));

		// set some data
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);

		List<Pair<LocalDate, Double>> momentumCurve = OspreyQuantMath.momentumCurve(2, hist);

		System.out.println(momentumCurve);

		Assert.assertTrue(750 < momentumCurve.size());
	}

	@Test
	public void stochasticTest1() throws Exception {

		LocalDate end = LocalDate.now();
		LocalDate start = end.minusYears(3).minusDays(10);
		QuoteDataFrequency freq = QuoteDataFrequency.DAY;

		String symbol = "QQQ";

		Security security = new Security(new SecurityKey(symbol, null));
		security.setInstrumentType(InstrumentType.STOCK);

		SecurityQuoteContainer sqc = yahooQuoteClient.quoteUltra(new SecurityKey(symbol, null));
		List<HistoricalQuote> hist = new ArrayList<>(
				yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start, end, freq));
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);

		StochasticOscillatorCurve curves = OspreyQuantMath.stochasticOscillatorSmaCurves(14, 1, 3, 5, hist);

		double[] pK = (double[]) curves.getRawDataMap().get(StochasticOscillatorCurve.PERCENT_K_KEY);
		double[] pD = (double[]) curves.getRawDataMap().get(StochasticOscillatorCurve.PERCENT_D_KEY);
		LocalDate[] dates = (LocalDate[]) curves.getRawDataMap().get(StochasticOscillatorCurve.DATE_KEY);

		for (int i = 0; i < curves.getLength(); i++) {
			System.out.println("%K: " + pK[i] + " %D: " + pD[i] + " date: " + dates[i]);
		}
	}

}
