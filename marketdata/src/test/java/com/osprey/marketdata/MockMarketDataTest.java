package com.osprey.marketdata;

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
import com.osprey.securitymaster.PricedSecurity;
import com.osprey.securitymaster.Security;

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
			PricedSecurity quote = mockMarketData.quote(s);
			Assert.assertEquals(s.getTicker(), quote.getTicker());
			Assert.assertEquals(s.getInstrumentType(), quote.getInstrumentType());

			Assert.assertTrue(quote.getAsk() > 0);
			Assert.assertTrue(quote.getBid() > 0);
			Assert.assertTrue(quote.getClose() > 0);
			Assert.assertTrue(quote.getOpen() > 0);
			Assert.assertTrue(quote.getLastPrice() > 0);
			Assert.assertTrue(quote.getVolume() > 0);
			System.out.println("Quote of " + s.getTicker() + " is " + quote);
		}
	}

	@Test
	public void mockSecurityPricerBatchTest1() throws Exception {
		Set<Security> securityMaster = securityMasterService.fetchSecurityMaster();

		System.out.println("Security Master : " + securityMaster);

		Assert.assertFalse(securityMaster.isEmpty());

		Map<Security, PricedSecurity> quoteBatch = mockMarketData.quoteBatch(securityMaster);

		for (PricedSecurity quote : quoteBatch.values()) {

			Assert.assertTrue(quote.getAsk() > 0);
			Assert.assertTrue(quote.getBid() > 0);
			Assert.assertTrue(quote.getClose() > 0);
			Assert.assertTrue(quote.getOpen() > 0);
			Assert.assertTrue(quote.getLastPrice() > 0);
			Assert.assertTrue(quote.getVolume() > 0);
			System.out.println("Quote of " + quote.getTicker() + " is " + quote);
		}
	}

}
