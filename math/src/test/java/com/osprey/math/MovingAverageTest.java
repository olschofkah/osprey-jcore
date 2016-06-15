package com.osprey.math;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.osprey.securitymaster.HistoricalSecurity;

public class MovingAverageTest {

	private static String TEST_TICKER_1 = "QQQ";
	private static double DOUBLE_TEST_DELTA = 0.00001;

	@Test
	public void testBasicSMA() throws Exception {

		List<HistoricalSecurity> closingPrices = generateHistoricalPrices();

		double sma = OspreyQuantMath.sma(10, closingPrices);

		Assert.assertEquals(28.5, sma, DOUBLE_TEST_DELTA);
	}
	
	@Test
	public void testVolatility1() throws Exception {

		List<HistoricalSecurity> closingPrices = generateHistoricalPrices();

		double vol = OspreyQuantMath.volatility(10, closingPrices);

		Assert.assertEquals(0.05648862, vol, DOUBLE_TEST_DELTA);
	}

	@Test
	public void testBasicEMA() throws Exception {

		List<HistoricalSecurity> closingPrices = generateHistoricalPrices();

		int p = 10;
		double sma = OspreyQuantMath.sma(p, closingPrices);
		double ema = OspreyQuantMath.ema(sma, p, closingPrices);

		Assert.assertEquals(29.97873696, ema, DOUBLE_TEST_DELTA);
	}

	private List<HistoricalSecurity> generateHistoricalPrices() {
		ZonedDateTime now = ZonedDateTime.now();

		HistoricalSecurity hs0 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(10));
		hs0.setClose(24);

		HistoricalSecurity hs1 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(9));
		hs1.setClose(25);

		HistoricalSecurity hs2 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(8));
		hs2.setClose(26);

		HistoricalSecurity hs3 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(7));
		hs3.setClose(27);

		HistoricalSecurity hs4 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(6));
		hs4.setClose(28);

		HistoricalSecurity hs5 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(5));
		hs5.setClose(29);

		HistoricalSecurity hs6 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(4));
		hs6.setClose(30);

		HistoricalSecurity hs7 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(3));
		hs7.setClose(31);

		HistoricalSecurity hs8 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(2));
		hs8.setClose(32);

		HistoricalSecurity hs9 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(1));
		hs9.setClose(33);

		List<HistoricalSecurity> closingPrices = new ArrayList<HistoricalSecurity>(10);
		closingPrices.add(hs0);
		closingPrices.add(hs1);
		closingPrices.add(hs2);
		closingPrices.add(hs3);
		closingPrices.add(hs4);
		closingPrices.add(hs5);
		closingPrices.add(hs6);
		closingPrices.add(hs7);
		closingPrices.add(hs8);
		closingPrices.add(hs9);
		return closingPrices;
	}

}
