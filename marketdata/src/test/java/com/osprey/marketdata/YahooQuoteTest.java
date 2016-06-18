package com.osprey.marketdata;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osprey.marketdata.feed.yahoo.YahooQuoteClient;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.Security;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MarketdataApplication.class)
public class YahooQuoteTest {

	@Autowired
	private YahooQuoteClient yahooQuoteClient;
	
	// TODO Add more test cases
	// TODO Add Test Suites

	@Test
	public void quoteFundamentalTest1() {

		long n0 = System.currentTimeMillis();
		FundamentalPricedSecurity quote = yahooQuoteClient.quoteFundamental(new Security("AAPL"));
		long n1 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteFundamental(new Security("GOOGL"));
		long n2 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteFundamental(new Security("QQQ"));
		long n3 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteFundamental(new Security("FB"));
		long n4 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteFundamental(new Security("BRKA"));
		long n5 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteFundamental(new Security("WMT"));
		long n6 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteFundamental(new Security("MCK"));
		long n7 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteFundamental(new Security("QQQ"));
		long n8 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteFundamental(new Security("SPY"));
		long n9 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteFundamental(new Security("XBI"));
		long n10 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteFundamental(new Security("UWTI"));
		long n11 = System.currentTimeMillis();
		quote = yahooQuoteClient.quoteFundamental(new Security("USO"));
		long n12 = System.currentTimeMillis();

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

}
