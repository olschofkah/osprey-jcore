package com.osprey.marketdata;

import java.time.LocalDate;
import java.time.ZonedDateTime;
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
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;
import com.osprey.securitymaster.PricedSecurity;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.constants.EarningsReportTime;
import com.osprey.securitymaster.utils.OspreyUtils;

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

	@Test
	public void mockSecurityFundamentalPricerIndividualTest1() throws Exception {
		Set<Security> securityMaster = securityMasterService.fetchSecurityMaster();

		Assert.assertFalse(securityMaster.isEmpty());

		LocalDate now = LocalDate.now();

		for (Security s : securityMaster) {
			FundamentalPricedSecurity quote = mockMarketData.quoteFundamental(s);
			Assert.assertEquals(s.getTicker(), quote.getTicker());
			Assert.assertEquals(s.getInstrumentType(), quote.getInstrumentType());

			Assert.assertTrue(quote.getAsk() > 0);
			Assert.assertTrue(quote.getBid() > 0);
			Assert.assertTrue(quote.getClose() > 0);
			Assert.assertTrue(quote.getOpen() > 0);
			Assert.assertTrue(quote.getLastPrice() > 0);
			Assert.assertTrue(quote.getVolume() > 0);

			Assert.assertTrue(quote.get_52High() > 0);
			Assert.assertTrue(quote.get_52Low() > 0);
			Assert.assertTrue(quote.getAnnualDividend() > 0);
			Assert.assertTrue(quote.getAnnualYield() > 0);
			Assert.assertTrue(quote.getBeta() > 0);
			Assert.assertTrue(quote.getDayHigh() > 0);
			Assert.assertTrue(quote.getDayLow() > 0);
			Assert.assertTrue(quote.getEps() > 0);
			Assert.assertTrue(quote.getHistoricalVolatility() > 0);
			Assert.assertTrue(quote.getMarketCap() > 0);
			Assert.assertTrue(quote.getNextDivDate().isAfter(now) || quote.getNextDivDate().isEqual(now));
			Assert.assertTrue(quote.getNextEarningsDate().isAfter(now) || quote.getNextEarningsDate().isEqual(now));
			Assert.assertTrue(quote.getNextEarningsReportTime() == EarningsReportTime.PRE_MARKET);
			Assert.assertTrue(quote.getPctHeldByInst() > 0);
			Assert.assertTrue(quote.getPeRatio() > 0);
			Assert.assertTrue(quote.getSharesOutstanding() > 0);
			Assert.assertTrue(quote.getShortInt() > 0);

			System.out.println("Quote of " + s.getTicker() + " is " + quote);
		}
	}

	@Test
	public void mockSecurityFundamentalPricerBatchTest1() throws Exception {
		Set<Security> securityMaster = securityMasterService.fetchSecurityMaster();

		System.out.println("Security Master : " + securityMaster);

		Assert.assertFalse(securityMaster.isEmpty());

		LocalDate now = LocalDate.now();

		Map<Security, FundamentalPricedSecurity> quoteBatch = mockMarketData.quoteFundamentalBatch(securityMaster);

		for (FundamentalPricedSecurity quote : quoteBatch.values()) {

			Assert.assertTrue(quote.getAsk() > 0);
			Assert.assertTrue(quote.getBid() > 0);
			Assert.assertTrue(quote.getClose() > 0);
			Assert.assertTrue(quote.getOpen() > 0);
			Assert.assertTrue(quote.getLastPrice() > 0);
			Assert.assertTrue(quote.getVolume() > 0);
			Assert.assertTrue(quote.get_52High() > 0);
			Assert.assertTrue(quote.get_52Low() > 0);
			Assert.assertTrue(quote.getAnnualDividend() > 0);
			Assert.assertTrue(quote.getAnnualYield() > 0);
			Assert.assertTrue(quote.getBeta() > 0);
			Assert.assertTrue(quote.getDayHigh() > 0);
			Assert.assertTrue(quote.getDayLow() > 0);
			Assert.assertTrue(quote.getEps() > 0);
			Assert.assertTrue(quote.getHistoricalVolatility() > 0);
			Assert.assertTrue(quote.getMarketCap() > 0);
			Assert.assertTrue(quote.getNextDivDate().isAfter(now) || quote.getNextDivDate().isEqual(now));
			Assert.assertTrue(quote.getNextEarningsDate().isAfter(now) || quote.getNextEarningsDate().isEqual(now));
			Assert.assertTrue(quote.getNextEarningsReportTime() == EarningsReportTime.PRE_MARKET);
			Assert.assertTrue(quote.getPctHeldByInst() > 0);
			Assert.assertTrue(quote.getPeRatio() > 0);
			Assert.assertTrue(quote.getSharesOutstanding() > 0);
			Assert.assertTrue(quote.getShortInt() > 0);
			System.out.println("Quote of " + quote.getTicker() + " is " + quote);
		}
	}

	@Test
	public void mockHistoricalPricerIndividualTest1() throws Exception {
		Set<Security> securityMaster = securityMasterService.fetchSecurityMaster();

		System.out.println("Security Master : " + securityMaster);

		Assert.assertFalse(securityMaster.isEmpty());

		ZonedDateTime start = OspreyUtils.getZonedDateTimeAtStartOfDay();
		ZonedDateTime end = start.minusDays(252);

		for (Security s : securityMaster) {
			List<HistoricalSecurity> hist = mockMarketData.fetchHistorical(s, start, end);

			Assert.assertEquals(252, hist.size());

			ZonedDateTime dayCounter = start;

			for (HistoricalSecurity hs : hist) {
				Assert.assertEquals(s.getTicker(), hs.getTicker());

				Assert.assertEquals(dayCounter, hs.getHistoricalDate());

				Assert.assertTrue(hs.getHigh() > 0);
				Assert.assertTrue(hs.getLow() > 0);
				Assert.assertTrue(hs.getClose() > 0);
				Assert.assertTrue(hs.getOpen() > 0);
				Assert.assertTrue(hs.getAdjClose() > 0);
				Assert.assertTrue(hs.getVolume() > 0);
				System.out.println("Quote of " + s.getTicker() + " is " + hs);

				dayCounter = dayCounter.minusDays(1);
			}
		}
	}

	@Test
	public void mockHistoricalPricerBatchTest1() throws Exception {
		Set<Security> securityMaster = securityMasterService.fetchSecurityMaster();

		System.out.println("Security Master : " + securityMaster);

		Assert.assertFalse(securityMaster.isEmpty());

		ZonedDateTime start = OspreyUtils.getZonedDateTimeAtStartOfDay();
		ZonedDateTime end = start.minusDays(252);

		Map<Security, List<HistoricalSecurity>> histBatch = mockMarketData.fetchHistoricalBatch(securityMaster, start,
				end);

		Assert.assertEquals(securityMaster.size(), histBatch.keySet().size());

		for (List<HistoricalSecurity> hist : histBatch.values()) {
			ZonedDateTime dayCounter = start;
			for (HistoricalSecurity hs : hist) {

				Assert.assertEquals(dayCounter, hs.getHistoricalDate());

				Assert.assertTrue(hs.getHigh() > 0);
				Assert.assertTrue(hs.getLow() > 0);
				Assert.assertTrue(hs.getClose() > 0);
				Assert.assertTrue(hs.getOpen() > 0);
				Assert.assertTrue(hs.getAdjClose() > 0);
				Assert.assertTrue(hs.getVolume() > 0);

				dayCounter = dayCounter.minusDays(1);
			}
		}
	}

}
