package com.osprey.math;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.osprey.securitymaster.HistoricalSecurity;

import junit.framework.Assert;

public class MovingAverageTest {

	private static String TEST_TICKER_1 = "QQQ";
	private static double DOUBLE_TEST_DELTA = 0.00001;

	@Test
	public void testBasicSMA() throws Exception {

		HistoricalSecurity hs0 = new HistoricalSecurity();
		hs0.setClose(24);
		hs0.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs1 = new HistoricalSecurity();
		hs1.setClose(25);
		hs1.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs2 = new HistoricalSecurity();
		hs2.setClose(26);
		hs2.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs3 = new HistoricalSecurity();
		hs3.setClose(27);
		hs3.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs4 = new HistoricalSecurity();
		hs4.setClose(28);
		hs4.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs5 = new HistoricalSecurity();
		hs5.setClose(29);
		hs5.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs6 = new HistoricalSecurity();
		hs6.setClose(30);
		hs6.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs7 = new HistoricalSecurity();
		hs7.setClose(31);
		hs7.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs8 = new HistoricalSecurity();
		hs8.setClose(32);
		hs8.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs9 = new HistoricalSecurity();
		hs9.setClose(33);
		hs9.setTicker(TEST_TICKER_1);

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

		double sma = OspreyQuantMath.sma(10, closingPrices);

		Assert.assertEquals(28.5, sma, DOUBLE_TEST_DELTA);
	}

	@Test
	public void testBasicEMA() throws Exception {

		HistoricalSecurity hs0 = new HistoricalSecurity();
		hs0.setClose(24);
		hs0.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs1 = new HistoricalSecurity();
		hs1.setClose(25);
		hs1.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs2 = new HistoricalSecurity();
		hs2.setClose(26);
		hs2.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs3 = new HistoricalSecurity();
		hs3.setClose(27);
		hs3.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs4 = new HistoricalSecurity();
		hs4.setClose(28);
		hs4.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs5 = new HistoricalSecurity();
		hs5.setClose(29);
		hs5.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs6 = new HistoricalSecurity();
		hs6.setClose(30);
		hs6.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs7 = new HistoricalSecurity();
		hs7.setClose(31);
		hs7.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs8 = new HistoricalSecurity();
		hs8.setClose(32);
		hs8.setTicker(TEST_TICKER_1);

		HistoricalSecurity hs9 = new HistoricalSecurity();
		hs9.setClose(33);
		hs9.setTicker(TEST_TICKER_1);

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

		int p = 10;
		double sma = OspreyQuantMath.sma(p, closingPrices);
		double ema = OspreyQuantMath.ema(sma, p, closingPrices);

		Assert.assertEquals(29.97873696, ema, DOUBLE_TEST_DELTA);
	}

}
