package com.osprey.math;

import java.time.LocalDate;
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

	public void testBeta() throws Exception {

		List<HistoricalSecurity> closingPrices = generateHistoricalPrices();
		List<HistoricalSecurity> closingPrices_bmk = generateHistoricalPrices_bmk();
		

		double beta = OspreyQuantMath.Beta(10, closingPrices, closingPrices_bmk);

		Assert.assertEquals(1.07, beta, DOUBLE_TEST_DELTA);
	}
	@Test
	public void testBasicEMA() throws Exception {

		List<HistoricalSecurity> closingPrices = generateHistoricalPrices();

		int p = 26;
		double sma = OspreyQuantMath.sma(p, closingPrices);
		double ema = OspreyQuantMath.ema(sma, p, closingPrices);

		Assert.assertEquals(36.194239, ema, DOUBLE_TEST_DELTA);
	}
	
	// 12 day ema (31.012363486) - 26 day ema (36.194239) = 5.181875 
	public void testMACD() throws Exception {

		List<HistoricalSecurity> closingPrices = generateHistoricalPrices();

		int long_len = 26;
		int short_len = 12;
		

		double MACD = OspreyQuantMath.MACD(long_len, short_len, closingPrices);
		
		Assert.assertEquals(5.1818755, MACD, DOUBLE_TEST_DELTA);
	}

	private List<HistoricalSecurity> generateHistoricalPrices() {
		LocalDate now = LocalDate.now();

		HistoricalSecurity hs0 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(26));
		hs0.setClose(24);

		HistoricalSecurity hs1 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(25));
		hs1.setClose(25);

		HistoricalSecurity hs2 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(24));
		hs2.setClose(26);

		HistoricalSecurity hs3 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(23));
		hs3.setClose(27);

		HistoricalSecurity hs4 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(22));
		hs4.setClose(28);

		HistoricalSecurity hs5 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(21));
		hs5.setClose(29);

		HistoricalSecurity hs6 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(20));
		hs6.setClose(30);

		HistoricalSecurity hs7 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(19));
		hs7.setClose(31);

		HistoricalSecurity hs8 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(18));
		hs8.setClose(32);

		HistoricalSecurity hs9 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(17));
		hs9.setClose(33);
		
		HistoricalSecurity hs10 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(16));
		hs10.setClose(33.5);
		
		HistoricalSecurity hs11 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(15));
		hs11.setClose(34);

		HistoricalSecurity hs12 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(14));
		hs12.setClose(34.5);
		
		HistoricalSecurity hs13 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(13));
		hs13.setClose(35);
		
		HistoricalSecurity hs14 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(12));
		hs14.setClose(35.5);
		
		HistoricalSecurity hs15 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(11));
		hs15.setClose(36);
		
		HistoricalSecurity hs16 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(10));
		hs16.setClose(36.5);
		
		HistoricalSecurity hs17 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(9));
		hs17.setClose(37);
		
		HistoricalSecurity hs18 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(8));
		hs18.setClose(37.5);
		
		HistoricalSecurity hs19 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(8));
		hs19.setClose(38);
		
		HistoricalSecurity hs20 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(7));
		hs20.setClose(38.5);
		
		HistoricalSecurity hs21 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(6));
		hs21.setClose(39);
		
		HistoricalSecurity hs22 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(5));
		hs22.setClose(39.5);
		
		HistoricalSecurity hs23 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(4));
		hs23.setClose(40);
		
		HistoricalSecurity hs24 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(3));
		hs24.setClose(40.5);
		
		HistoricalSecurity hs25 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(2));
		hs25.setClose(41);
		
		HistoricalSecurity hs26 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(1));
		hs26.setClose(41.5);
		
	
		

		List<HistoricalSecurity> closingPrices = new ArrayList<HistoricalSecurity>(26);
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
		closingPrices.add(hs10);
		closingPrices.add(hs11);
		closingPrices.add(hs12);
		closingPrices.add(hs13);
		closingPrices.add(hs14);
		closingPrices.add(hs15);
		closingPrices.add(hs16);
		closingPrices.add(hs17);
		closingPrices.add(hs18);
		closingPrices.add(hs19);
		closingPrices.add(hs20);
		closingPrices.add(hs21);
		closingPrices.add(hs22);
		closingPrices.add(hs23);
		closingPrices.add(hs24);
		closingPrices.add(hs25);
		closingPrices.add(hs26);


		return closingPrices;
	}
	
	
	private List<HistoricalSecurity> generateHistoricalPrices_bmk() {
		LocalDate now = LocalDate.now();

		HistoricalSecurity hs0 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(10));
		hs0.setClose(25);

		HistoricalSecurity hs1 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(9));
		hs1.setClose(26);

		HistoricalSecurity hs2 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(8));
		hs2.setClose(27);

		HistoricalSecurity hs3 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(7));
		hs3.setClose(28);

		HistoricalSecurity hs4 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(6));
		hs4.setClose(29);

		HistoricalSecurity hs5 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(5));
		hs5.setClose(30);

		HistoricalSecurity hs6 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(4));
		hs6.setClose(31);

		HistoricalSecurity hs7 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(3));
		hs7.setClose(32);

		HistoricalSecurity hs8 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(2));
		hs8.setClose(33);

		HistoricalSecurity hs9 = new HistoricalSecurity(TEST_TICKER_1, now.minusDays(1));
		hs9.setClose(34);

		List<HistoricalSecurity> closingPrices_bmk = new ArrayList<HistoricalSecurity>(10);
		closingPrices_bmk.add(hs0);
		closingPrices_bmk.add(hs1);
		closingPrices_bmk.add(hs2);
		closingPrices_bmk.add(hs3);
		closingPrices_bmk.add(hs4);
		closingPrices_bmk.add(hs5);
		closingPrices_bmk.add(hs6);
		closingPrices_bmk.add(hs7);
		closingPrices_bmk.add(hs8);
		closingPrices_bmk.add(hs9);
		return closingPrices_bmk;
	}

}
