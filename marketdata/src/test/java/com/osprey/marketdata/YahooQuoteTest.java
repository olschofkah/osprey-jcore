package com.osprey.marketdata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osprey.marketdata.feed.constants.QuoteDataFrequency;
import com.osprey.marketdata.feed.yahoo.YahooHistoricalQuoteClient;
import com.osprey.marketdata.feed.yahoo.YahooQuoteClient;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MarketdataApplication.class)
public class YahooQuoteTest {

	@Autowired
	private YahooQuoteClient yahooQuoteClient;
	@Autowired
	private YahooHistoricalQuoteClient yahooHistoricalQuoteClient;

	// TODO Add more test cases
	// TODO Add Test Suites

	@Test
	public void quoteFundamentalTest1() throws Exception {

		// TODO see why first request takes oddly long

		long n0 = System.currentTimeMillis();
		SecurityQuoteContainer quote = yahooQuoteClient.quoteUltra(new SecurityKey("USO", null));
		long n1 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteUltra(new SecurityKey("GOOGL", null));
		long n2 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteUltra(new SecurityKey("QQQ", null));
		long n3 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteUltra(new SecurityKey("FB", null));
		long n4 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteUltra(new SecurityKey("BRK-A", null));
		long n5 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteUltra(new SecurityKey("WMT", null));
		long n6 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteUltra(new SecurityKey("MCK", null));
		long n7 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteUltra(new SecurityKey("QQQ", null));
		long n8 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteUltra(new SecurityKey("SPY", null));
		long n9 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteUltra(new SecurityKey("XBI", null));
		long n10 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteUltra(new SecurityKey("UWTI", null));
		long n11 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteUltra(new SecurityKey("AAPL", null));
		long n12 = System.currentTimeMillis();

		Assert.assertEquals("AAPL", quote.getKey().getSymbol());
		System.out.println(quote);

		List<Long> results = new ArrayList<>();
		results.add(n1 - n0);
		results.add(n2 - n1);
		results.add(n3 - n2);
		results.add(n4 - n3);
		results.add(n5 - n4);
		results.add(n6 - n5);
		results.add(n7 - n6);
		results.add(n8 - n7);
		results.add(n9 - n8);
		results.add(n10 - n9);
		results.add(n11 - n10);
		results.add(n12 - n11);

		for (int i = 0; i < results.size(); ++i) {
			System.out.println(results.get(i));
			Assert.assertTrue("Iteration " + i + " is longer than acceptable", results.get(i) < 1000);
		}
		double averageDuration = ((double) (n12 - n0)) / 12.0;

		System.out.println("Average time in mills: " + averageDuration);
		Assert.assertTrue("Average duration failure", averageDuration < 250);

	}

	@Test
	public void problemTickersTest() throws Exception {

		yahooQuoteClient.quoteUltra(new SecurityKey("SKX", null));
		yahooQuoteClient.quoteUltra(new SecurityKey("BBBY", null));

		// only ensuring the quotes do not throw an exception ... and we must
		// have ASSERTS!
		Assert.assertTrue(true);

	}

	@Test
	public void quoteHistoricalTest1() throws Exception {

		// TODO see why first request takes oddly long

		LocalDate end = LocalDate.now();
		LocalDate start = end.minusYears(1).minusDays(10);
		QuoteDataFrequency freq = QuoteDataFrequency.DAY;

		long n0 = System.currentTimeMillis();
		List<HistoricalQuote> hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("AAPL", null), start,
				end, freq);
		long n1 = System.currentTimeMillis();
		hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("GOOGL", null), start, end, freq);
		long n2 = System.currentTimeMillis();
		hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("QQQ", null), start, end, freq);
		long n3 = System.currentTimeMillis();
		hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("FB", null), start, end, freq);
		long n4 = System.currentTimeMillis();
		hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("BRK-A", null), start, end, freq);
		long n5 = System.currentTimeMillis();
		hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("WMT", null), start, end, freq);
		long n6 = System.currentTimeMillis();
		hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("MCK", null), start, end, freq);
		long n7 = System.currentTimeMillis();
		hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("QQQ", null), start, end, freq);
		long n8 = System.currentTimeMillis();
		hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("SPY", null), start, end, freq);
		long n9 = System.currentTimeMillis();
		hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("XBI", null), start, end, freq);
		long n10 = System.currentTimeMillis();
		hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("UWTI", null), start, end, freq);
		long n11 = System.currentTimeMillis();
		hist = yahooHistoricalQuoteClient.quoteHistorical(new SecurityKey("USO", null), start, end, freq);
		long n12 = System.currentTimeMillis();

		Assert.assertTrue(hist.size() < 262 && hist.size() > 252);

		List<Long> results = new ArrayList<>();
		results.add(n1 - n0);
		results.add(n2 - n1);
		results.add(n3 - n2);
		results.add(n4 - n3);
		results.add(n5 - n4);
		results.add(n6 - n5);
		results.add(n7 - n6);
		results.add(n8 - n7);
		results.add(n9 - n8);
		results.add(n10 - n9);
		results.add(n11 - n10);
		results.add(n12 - n11);

		for (int i = 0; i < results.size(); ++i) {
			System.out.println(results.get(i));
			Assert.assertTrue("Iteration " + i + " is longer than acceptable", results.get(i) < 300);
		}
		double averageDuration = ((double) (n12 - n0)) / 12.0;

		System.out.println("Average time in mills: " + averageDuration);
		Assert.assertTrue("Average duration failure", averageDuration < 100);

	}
}
