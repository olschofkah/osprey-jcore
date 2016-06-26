package com.osprey.marketdata;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.osprey.marketdata.feed.mock.MockMarketDataFeedService;
import com.osprey.marketdata.feed.mock.MockSecurityMasterService;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MarketDataDevConfiguration.class)
public class MockMarketDataTest {

	@Autowired
	private MockMarketDataFeedService mockMarketData;

	@Autowired
	private MockSecurityMasterService securityMasterService;

	@Test
	public void mockSecurityMasterTest1() throws Exception {
		Set<Security> securityMaster = securityMasterService.fetchSecurityMaster();

		System.out.println("Security Master : " + securityMaster);

		Assert.assertFalse(securityMaster.isEmpty());
	}

	@Test
	public void mockSecurityPricerIndividualTest1() throws Exception {
		Set<Security> securityMaster = securityMasterService.fetchSecurityMaster();

		System.out.println("Security Master : " + securityMaster);

		Assert.assertFalse(securityMaster.isEmpty());

		for (Security s : securityMaster) {
			SecurityQuote quote = mockMarketData.quote(s.getKey());
			Assert.assertEquals(s.getKey().getSymbol(), quote.getKey().getSymbol());

			Assert.assertTrue(quote.getAsk() > 0);
			Assert.assertTrue(quote.getBid() > 0);
			Assert.assertTrue(quote.getClose() > 0);
			Assert.assertTrue(quote.getOpen() > 0);
			Assert.assertTrue(quote.getLast() > 0);
			Assert.assertTrue(quote.getVolume() > 0);
			System.out.println("Quote of " + s.getKey().getSymbol() + " is " + quote);
		}
	}

	@Test
	public void mockSecurityPricerBatchTest1() throws Exception {
		Set<Security> securityMaster = securityMasterService.fetchSecurityMaster();

		System.out.println("Security Master : " + securityMaster);

		Assert.assertFalse(securityMaster.isEmpty());

		Set<SecurityKey> keySet = new HashSet<>(securityMaster.size());
		for (Security security : securityMaster) {
			keySet.add(security.getKey());
		}

		Map<SecurityKey, SecurityQuote> quoteBatch = mockMarketData.quoteBatch(keySet);

		for (SecurityQuote quote : quoteBatch.values()) {

			Assert.assertTrue(quote.getAsk() > 0);
			Assert.assertTrue(quote.getBid() > 0);
			Assert.assertTrue(quote.getClose() > 0);
			Assert.assertTrue(quote.getOpen() > 0);
			Assert.assertTrue(quote.getLast() > 0);
			Assert.assertTrue(quote.getVolume() > 0);
			System.out.println("Quote of " + quote.getKey().getSymbol() + " is " + quote);
		}
	}

	@Test
	public void mockSecurityFundamentalPricerIndividualTest1() throws Exception {
		Set<Security> securityMaster = securityMasterService.fetchSecurityMaster();

		Assert.assertFalse(securityMaster.isEmpty());

		for (Security s : securityMaster) {
			SecurityQuoteContainer quote = mockMarketData.quoteUltra(s);
			quote.setSecurity(s);

			Assert.assertEquals(s.getKey().getSymbol(), quote.getKey().getSymbol());
			Assert.assertEquals(s.getInstrumentType(), quote.getSecurity().getInstrumentType());

			Assert.assertTrue(quote.getSecurityQuote().getAsk() > 0);
			Assert.assertTrue(quote.getSecurityQuote().getBid() > 0);
			Assert.assertTrue(quote.getSecurityQuote().getClose() > 0);
			Assert.assertTrue(quote.getSecurityQuote().getOpen() > 0);
			Assert.assertTrue(quote.getSecurityQuote().getLast() > 0);
			Assert.assertTrue(quote.getSecurityQuote().getVolume() > 0);

			// TODO add tests for fundamentals

			System.out.println("Quote of " + s.getKey().getSymbol() + " is " + quote);
		}
	}


	@Test
	public void mockHistoricalPricerIndividualTest1() throws Exception {
		Set<Security> securityMaster = securityMasterService.fetchSecurityMaster();

		System.out.println("Security Master : " + securityMaster);

		Assert.assertFalse(securityMaster.isEmpty());

		LocalDate start = LocalDate.now();
		LocalDate end = start.minusDays(252);

		for (Security s : securityMaster) {
			List<HistoricalQuote> hist = mockMarketData.quoteHistorical(s.getKey(), start, end, null);

			Assert.assertEquals(252, hist.size());

			LocalDate dayCounter = start;

			for (HistoricalQuote hs : hist) {
				Assert.assertEquals(s.getKey().getSymbol(), hs.getKey().getSymbol());

				Assert.assertEquals(dayCounter, hs.getHistoricalDate());

				Assert.assertTrue(hs.getHigh() > 0);
				Assert.assertTrue(hs.getLow() > 0);
				Assert.assertTrue(hs.getClose() > 0);
				Assert.assertTrue(hs.getOpen() > 0);
				Assert.assertTrue(hs.getAdjClose() > 0);
				Assert.assertTrue(hs.getVolume() > 0);
				System.out.println("Quote of " + s.getKey().getSymbol() + " is " + hs);

				dayCounter = dayCounter.minusDays(1);
			}
		}
	}

}
