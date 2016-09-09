package com.osprey.marketdata;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osprey.marketdata.feed.ychart.YChartHistoricalEventsClient;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuoteContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("integration-test")
@SpringApplicationConfiguration(classes = MarketDataTestConfiguration.class)
public class YChartEventsTest {

	@Autowired
	private YChartHistoricalEventsClient ychartClient;


	@Test
	public void quoteFundamentalTest1() throws Exception {
//		
//		long n0 = System.currentTimeMillis();
//		SecurityQuoteContainer quote = yahooQuoteClient.quoteUltra(new SecurityKey("USO", null));
//		long n1 = System.currentTimeMillis();
//		quote = yahooQuoteClient.quoteUltra(new SecurityKey("GOOGL", null));
//		long n2 = System.currentTimeMillis();
//		quote = yahooQuoteClient.quoteUltra(new SecurityKey("QQQ", null));
//		long n3 = System.currentTimeMillis();
//		quote = yahooQuoteClient.quoteUltra(new SecurityKey("FB", null));
//		long n4 = System.currentTimeMillis();
//		quote = yahooQuoteClient.quoteUltra(new SecurityKey("BRK-A", null));
//		long n5 = System.currentTimeMillis();
//		quote = yahooQuoteClient.quoteUltra(new SecurityKey("WMT", null));
//		long n6 = System.currentTimeMillis();
//		quote = yahooQuoteClient.quoteUltra(new SecurityKey("MCK", null));
//		long n7 = System.currentTimeMillis();
//		quote = yahooQuoteClient.quoteUltra(new SecurityKey("QQQ", null));
//		long n8 = System.currentTimeMillis();
//		quote = yahooQuoteClient.quoteUltra(new SecurityKey("SPY", null));
//		long n9 = System.currentTimeMillis();
//		quote = yahooQuoteClient.quoteUltra(new SecurityKey("XBI", null));
//		long n10 = System.currentTimeMillis();
//		quote = yahooQuoteClient.quoteUltra(new SecurityKey("UWTI", null));
//		long n11 = System.currentTimeMillis();
//		quote = yahooQuoteClient.quoteUltra(new SecurityKey("AAPL", null));
//		long n12 = System.currentTimeMillis();
//
//		Assert.assertEquals("AAPL", quote.getKey().getSymbol());
//		System.out.println(quote);
//
//		List<Long> results = new ArrayList<>();
//		results.add(n1 - n0);
//		results.add(n2 - n1);
//		results.add(n3 - n2);
//		results.add(n4 - n3);
//		results.add(n5 - n4);
//		results.add(n6 - n5);
//		results.add(n7 - n6);
//		results.add(n8 - n7);
//		results.add(n9 - n8);
//		results.add(n10 - n9);
//		results.add(n11 - n10);
//		results.add(n12 - n11);
//
//		for (int i = 0; i < results.size(); ++i) {
//			System.out.println(results.get(i));
//			Assert.assertTrue("Iteration " + i + " is longer than acceptable", results.get(i) < 1000);
//		}
//		double averageDuration = ((double) (n12 - n0)) / 12.0;
//
//		System.out.println("Average time in mills: " + averageDuration);
//		Assert.assertTrue("Average duration failure", averageDuration < 250);

	}

	@Test
	public void simpleTest() throws Exception {
		SecurityKey securityKey = new SecurityKey("RBS", null);
		SecurityQuoteContainer sqc = new SecurityQuoteContainer(securityKey);
		ychartClient.populateEvents(sqc);

		System.out.println(sqc.getEvents());

		Assert.assertTrue(sqc.getEvents().size() > 4);
	}

}
