package com.osprey.math;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.osprey.math.result.BollingerBandTimeSeries;
import com.osprey.math.result.MovingAverageType;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.constants.OptionType;

public class MathLibTest {

	private static String TEST_TICKER_1 = "QQQ";
	private static double DOUBLE_TEST_DELTA = 0.00001;

	@Test
	public void testBasicSMA() throws Exception {

		List<HistoricalQuote> closingPrices = generateHistoricalPrices();
		SecurityQuote quote = new SecurityQuote(TEST_TICKER_1);
		quote.setLast(closingPrices.get(0).getAdjClose());

		double sma = OspreyQuantMath.sma(10, 0, closingPrices);

		Assert.assertEquals(27.2, sma, DOUBLE_TEST_DELTA);
	}

	@Test
	public void testRSI() throws Exception {

		List<HistoricalQuote> closingPrices = generateHistoricalPrices();

		int p = 14;
		int offset = 0;
		double rsi = OspreyQuantMath.rsiUsingSma(p, offset, closingPrices);

		Assert.assertEquals(33.80281690, rsi, DOUBLE_TEST_DELTA);
	}

	@Test
	public void testVolatility1() throws Exception {

		List<HistoricalQuote> closingPrices = generateHistoricalPrices();

		double vol = OspreyQuantMath.volatility(10, closingPrices);
		System.out.println(vol);
		Assert.assertEquals(0.073598628, vol, DOUBLE_TEST_DELTA);
	}


	
	@Test
	public void testBeta() throws Exception {

		List<HistoricalQuote> closingPrices = generateHistoricalPrices();
		List<HistoricalQuote> closingPrices_bmk = generateHistoricalPrices_bmk();

		double beta = OspreyQuantMath.beta(10, closingPrices, closingPrices_bmk);

		Assert.assertEquals(0.0302842, beta, DOUBLE_TEST_DELTA);
	}

	/**
	 * test case can be verified in
	 * http://www.wolframalpha.com/input/?i=black+scholes&rawformassumption=%7B%22FP%22,+%22FinancialOption%22,+%22OptionName%22%7D+-%3E+%22VanillaEuropean%22&rawformassumption=%7B%22FP%22,+%22FinancialOption%22,+%22opttype%22%7D+-%3E+%22Call%22&rawformassumption=%7B%22F%22,+%22FinancialOption%22,+%22strike%22%7D+-%3E%22$55%22&rawformassumption=%7B%22F%22,+%22FinancialOption%22,+%22exptime%22%7D+-%3E%224+mo%22&rawformassumption=%7B%22F%22,+%22FinancialOption%22,+%22underlying%22%7D+-%3E%22$56.25%22&rawformassumption=%7B%22F%22,+%22FinancialOption%22,+%22vol%22%7D+-%3E%2228+%25%22&rawformassumption=%7B%22F%22,+%22FinancialOption%22,+%22div%22%7D+-%3E%220%25%22&rawformassumption=%7B%22F%22,+%22FinancialOption%22,+%22rf%22%7D+-%3E%222.85%25%22&rawformassumption=%7B%22MC%22,+%22%22%7D+-%3E+%7B%22Formula%22%7D&rawformassumption=%7B%22MC%22,%22%22%7D-%3E%7B%22Formula%22%7D
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBSmodel() throws Exception {

		double s = 56.25;
		double k = 55;
		double r = 0.0285;
		double t = 0.34;
		double v = 0.28;
		OptionType type = OptionType.CALL;

		double option_price = OspreyQuantMath.blackScholes(type, s, k, t, r, v);
		double iv = OspreyQuantMath.impliedVolatility(type, k, s, t, option_price, r);
		Assert.assertEquals(0.279903411, iv, DOUBLE_TEST_DELTA);
	}

	@Test
	public void testBasicEMA() throws Exception {

		List<HistoricalQuote> closingPrices = generate252HistoricalPrices();

		SecurityQuote securityQuote = new SecurityQuote(TEST_TICKER_1);
		securityQuote.setLast(closingPrices.get(0).getAdjClose());

		int p = 50;

		double ema = OspreyQuantMath.ema(p, 0, closingPrices);

		Assert.assertEquals(475.50168, ema, DOUBLE_TEST_DELTA);
	}

	@Test
	public void testBollingerBandCalc() throws Exception {

		List<HistoricalQuote> closingPrices = generate252HistoricalPrices();
		BollingerBandTimeSeries bollingerBands = OspreyQuantMath.bollingerBands(20, 2.0, 22, closingPrices, MovingAverageType.SMA);

		Assert.assertEquals(490.5, ((double[]) bollingerBands.getRawDataMap().get(BollingerBandTimeSeries.MOVING_AVERAGE_KEY))[0],
				DOUBLE_TEST_DELTA);
		Assert.assertEquals(502.3321595661992,
				((double[]) bollingerBands.getRawDataMap().get(BollingerBandTimeSeries.UPPER_BAND_KEY))[0],
				DOUBLE_TEST_DELTA);
		Assert.assertEquals(478.6678404338008,
				((double[]) bollingerBands.getRawDataMap().get(BollingerBandTimeSeries.LOWER_BAND_KEY))[0],
				DOUBLE_TEST_DELTA);

	}

	private List<HistoricalQuote> generate252HistoricalPrices() {
		LocalDate now = LocalDate.now();
		List<HistoricalQuote> closingPrices = new ArrayList<HistoricalQuote>(253);

		for (int i = 0; i < 252; i++) {
			HistoricalQuote hs0 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(26));
			hs0.setAdjClose(500 - i);
			hs0.setClose(500 - i);
			hs0.setHigh(600 - i);
			hs0.setLow(400 - i);
			hs0.setOpen(499 - i);
			hs0.setVolume(10000000 - i * 100);

			closingPrices.add(hs0);
		}

		return closingPrices;
	}

	private List<HistoricalQuote> generateHistoricalPrices() {
		LocalDate now = LocalDate.now();

		HistoricalQuote hs0 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(26));
		hs0.setAdjClose(24);
		hs0.setClose(24);

		HistoricalQuote hs1 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(25));
		hs1.setAdjClose(25);
		hs1.setClose(25);

		HistoricalQuote hs2 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(24));
		hs2.setAdjClose(26);
		hs2.setClose(26);

		HistoricalQuote hs3 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(23));
		hs3.setAdjClose(27);
		hs3.setClose(27);

		HistoricalQuote hs4 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(22));
		hs4.setAdjClose(28);
		hs4.setClose(28);

		HistoricalQuote hs5 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(21));
		hs5.setAdjClose(29);
		hs5.setClose(29);

		HistoricalQuote hs6 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(20));
		hs6.setAdjClose(30);
		hs6.setClose(30);

		HistoricalQuote hs7 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(19));
		hs7.setAdjClose(31);
		hs7.setClose(31);

		HistoricalQuote hs8 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(18));
		hs8.setAdjClose(32);
		hs8.setClose(32);

		HistoricalQuote hs9 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(17));
		hs9.setAdjClose(20);
		hs9.setClose(20);

		HistoricalQuote hs10 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(16));
		hs10.setAdjClose(33.5);
		hs10.setClose(33.5);

		HistoricalQuote hs11 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(15));
		hs11.setAdjClose(34);
		hs11.setClose(34);

		HistoricalQuote hs12 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(14));
		hs12.setAdjClose(34.5);
		hs12.setClose(34.5);

		HistoricalQuote hs13 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(13));
		hs13.setAdjClose(35);
		hs13.setClose(35);

		HistoricalQuote hs14 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(12));
		hs14.setAdjClose(35.5);
		hs14.setClose(35.5);

		HistoricalQuote hs15 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(11));
		hs15.setAdjClose(36);
		hs15.setClose(36);

		HistoricalQuote hs16 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(10));
		hs16.setAdjClose(36.5);
		hs16.setClose(36.5);

		HistoricalQuote hs17 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(9));
		hs17.setAdjClose(37);
		hs17.setClose(37);

		HistoricalQuote hs18 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(8));
		hs18.setAdjClose(37.5);
		hs18.setClose(37.5);

		HistoricalQuote hs20 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(7));
		hs20.setAdjClose(38.5);
		hs20.setClose(38.5);

		HistoricalQuote hs21 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(6));
		hs21.setAdjClose(39);
		hs21.setClose(39);

		HistoricalQuote hs22 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(5));
		hs22.setAdjClose(39.5);
		hs22.setClose(39.5);

		HistoricalQuote hs23 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(4));
		hs23.setAdjClose(40);
		hs23.setClose(40);

		HistoricalQuote hs24 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(3));
		hs24.setAdjClose(40.5);
		hs24.setClose(40.5);

		HistoricalQuote hs25 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(2));
		hs25.setAdjClose(41);
		hs25.setClose(41);

		HistoricalQuote hs26 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(1));
		hs26.setAdjClose(41.5);
		hs26.setClose(41.5);

		List<HistoricalQuote> closingPrices = new ArrayList<HistoricalQuote>(26);
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
		closingPrices.add(hs20);
		closingPrices.add(hs21);
		closingPrices.add(hs22);
		closingPrices.add(hs23);
		closingPrices.add(hs24);
		closingPrices.add(hs25);
		closingPrices.add(hs26);
		
		Collections.sort(closingPrices, new Comparator<HistoricalQuote>(){

			@Override
			public int compare(HistoricalQuote o1, HistoricalQuote o2) {
				return o2.getHistoricalDate().compareTo(o1.getHistoricalDate());
			}
			
		});

		return closingPrices;
	}
	
	

	private List<HistoricalQuote> generateHistoricalPrices_bmk() {
		LocalDate now = LocalDate.now();

		HistoricalQuote hs0 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(10));
		hs0.setClose(25);
		hs0.setAdjClose(25);

		HistoricalQuote hs1 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(9));
		hs1.setClose(26);
		hs1.setAdjClose(26);

		HistoricalQuote hs2 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(8));
		hs2.setClose(27);
		hs2.setAdjClose(27);

		HistoricalQuote hs3 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(7));
		hs3.setClose(28);
		hs3.setAdjClose(28);

		HistoricalQuote hs4 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(6));
		hs4.setClose(29);
		hs4.setAdjClose(29);

		HistoricalQuote hs5 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(5));
		hs5.setClose(30);
		hs5.setAdjClose(30);

		HistoricalQuote hs6 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(4));
		hs6.setClose(31);
		hs6.setAdjClose(31);

		HistoricalQuote hs7 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(3));
		hs7.setClose(32);
		hs7.setAdjClose(32);

		HistoricalQuote hs8 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(2));
		hs8.setClose(33);
		hs8.setAdjClose(33);

		HistoricalQuote hs9 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(1));
		hs9.setClose(34);
		hs9.setAdjClose(34);

		List<HistoricalQuote> closingPrices_bmk = new ArrayList<HistoricalQuote>(10);
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

	private List<HistoricalQuote> generateHistoricalPrices_rsi() {
		LocalDate now = LocalDate.now();

		HistoricalQuote hs0 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(15));
		hs0.setClose(30);

		HistoricalQuote hs1 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(14));
		hs1.setClose(31);

		HistoricalQuote hs2 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(13));
		hs2.setClose(32);

		HistoricalQuote hs3 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(12));
		hs3.setClose(33);

		HistoricalQuote hs4 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(11));
		hs4.setClose(34);

		HistoricalQuote hs5 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(10));
		hs5.setClose(35);

		HistoricalQuote hs6 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(9));
		hs6.setClose(36);

		HistoricalQuote hs7 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(8));
		hs7.setClose(37);

		HistoricalQuote hs8 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(7));
		hs8.setClose(38);

		HistoricalQuote hs9 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(6));
		hs9.setClose(39);

		HistoricalQuote hs10 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(5));
		hs10.setClose(40);

		HistoricalQuote hs11 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(4));
		hs11.setClose(41);

		HistoricalQuote hs12 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(3));
		hs12.setClose(42);

		HistoricalQuote hs13 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(2));
		hs13.setClose(40);

		HistoricalQuote hs14 = new HistoricalQuote(TEST_TICKER_1, now.minusDays(1));
		hs14.setClose(38);

		List<HistoricalQuote> closingPrices_rsi = new ArrayList<HistoricalQuote>(15);
		closingPrices_rsi.add(hs0);
		closingPrices_rsi.add(hs1);
		closingPrices_rsi.add(hs2);
		closingPrices_rsi.add(hs3);
		closingPrices_rsi.add(hs4);
		closingPrices_rsi.add(hs5);
		closingPrices_rsi.add(hs6);
		closingPrices_rsi.add(hs7);
		closingPrices_rsi.add(hs8);
		closingPrices_rsi.add(hs9);
		closingPrices_rsi.add(hs10);
		closingPrices_rsi.add(hs11);
		closingPrices_rsi.add(hs12);
		closingPrices_rsi.add(hs13);
		closingPrices_rsi.add(hs14);

		return closingPrices_rsi;
	}
}
