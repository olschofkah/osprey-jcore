package com.osprey.screen;

import java.time.LocalDate;
import java.util.List;

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
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.InstrumentType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MarketdataApplication.class)
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
		List<HistoricalQuote> hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start,
				end, freq);
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
		List<HistoricalQuote> hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start,
				end, freq);
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);
		
		long currentTimeMillis = System.currentTimeMillis();
		double ema5 = OspreyQuantMath.ema(12, 0, hist);
		System.out.println(System.currentTimeMillis() - currentTimeMillis);

		//		
//		System.out.println(ema1 + " close:" + hist.get(4));
//		System.out.println(ema2 + " close:" + hist.get(3));
//		System.out.println(ema3 + " close:" + hist.get(2));
//		System.out.println(ema4 + " close:" + hist.get(1));
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
		List<HistoricalQuote> hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey(symbol, null), start,
				end, freq);
		sqc.setHistoricalQuotes(hist);
		sqc.setSecurity(security);
		
		System.out.println(OspreyQuantMath.rsiUsingSma(14, 0, hist));
		System.out.println(OspreyQuantMath.rsiUsingEma(14, 0, hist));
		System.out.println(OspreyQuantMath.rsiUsingWilders(14, 0, hist));
		System.out.println(OspreyQuantMath.wildersMovingAverage(14,0	, hist));
	}
	

}
