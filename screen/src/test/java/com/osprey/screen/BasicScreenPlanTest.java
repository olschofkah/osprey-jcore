package com.osprey.screen;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.osprey.screen.criteria.IStockScreenCriteria;
import com.osprey.screen.criteria.PreviousClosePriceCriteria;
import com.osprey.screen.criteria.RelationalOperator;
import com.osprey.screen.criteria.SimpleMovingAverageCriteria;
import com.osprey.screen.criteria.VolatilityCriteria;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;

public class BasicScreenPlanTest {

	private static String TEST_TICKER_1 = "QQQ";

	@Test
	public void testSinglePriceScreen1() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		FundamentalPricedSecurity s = new FundamentalPricedSecurity(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(5, RelationalOperator._GT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<FundamentalPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();

		factory.setSecurityUniverse(securities);

		List<StockScreenPlan> plans = factory.build(criteria);

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSinglePriceScreen2() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		FundamentalPricedSecurity s = new FundamentalPricedSecurity(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(500, RelationalOperator._GT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<FundamentalPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setSecurityUniverse(securities);

		List<StockScreenPlan> plans = factory.build(criteria);

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertFalse(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSinglePriceScreen3() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		FundamentalPricedSecurity s = new FundamentalPricedSecurity(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(25, RelationalOperator._EQ);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<FundamentalPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();

		factory.setSecurityUniverse(securities);

		List<StockScreenPlan> plans = factory.build(criteria);

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSinglePriceScreen4() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		FundamentalPricedSecurity s = new FundamentalPricedSecurity(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(5, RelationalOperator._GE);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<FundamentalPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();

		factory.setSecurityUniverse(securities);

		List<StockScreenPlan> plans = factory.build(criteria);

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSinglePriceScreen5() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		FundamentalPricedSecurity s = new FundamentalPricedSecurity(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(35, RelationalOperator._LT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<FundamentalPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();

		factory.setSecurityUniverse(securities);

		List<StockScreenPlan> plans = factory.build(criteria);

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSinglePriceScreen6() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		FundamentalPricedSecurity s = new FundamentalPricedSecurity(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(25.001, RelationalOperator._LE);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<FundamentalPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();

		factory.setSecurityUniverse(securities);

		List<StockScreenPlan> plans = factory.build(criteria);

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSingleSMAScreen1() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		FundamentalPricedSecurity s = new FundamentalPricedSecurity(TEST_TICKER_1);
		s.setClose(closingPrices.get(closingPrices.size() - 1).getClose());

		SimpleMovingAverageCriteria c1 = new SimpleMovingAverageCriteria(4, 8, RelationalOperator._GT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<FundamentalPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();

		factory.setSecurityUniverse(securities);

		List<StockScreenPlan> plans = factory.build(criteria);

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSingleSMAAndPriceScreen1() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		FundamentalPricedSecurity s = new FundamentalPricedSecurity(TEST_TICKER_1);
		s.setClose(closingPrices.get(closingPrices.size() - 1).getClose());

		SimpleMovingAverageCriteria c1 = new SimpleMovingAverageCriteria(4, 8, RelationalOperator._GT);
		PreviousClosePriceCriteria c2 = new PreviousClosePriceCriteria(25.00, RelationalOperator._GT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);
		criteria.add(c2);

		Map<FundamentalPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();

		factory.setSecurityUniverse(securities);

		List<StockScreenPlan> plans = factory.build(criteria);

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSingleSMAAndPriceScreen2() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		FundamentalPricedSecurity s = new FundamentalPricedSecurity(TEST_TICKER_1);
		s.setClose(closingPrices.get(closingPrices.size() - 1).getClose());

		SimpleMovingAverageCriteria c1 = new SimpleMovingAverageCriteria(4, 8, RelationalOperator._GT);
		PreviousClosePriceCriteria c2 = new PreviousClosePriceCriteria(55.00, RelationalOperator._GT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);
		criteria.add(c2);

		Map<FundamentalPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setSecurityUniverse(securities);

		List<StockScreenPlan> plans = factory.build(criteria);

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertFalse(resultSet.contains(TEST_TICKER_1));
	}
	
	@Test
	public void testSingleVolatilityScreen1() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		FundamentalPricedSecurity s = new FundamentalPricedSecurity(TEST_TICKER_1);
		s.setClose(closingPrices.get(closingPrices.size() - 1).getClose());

		VolatilityCriteria c1 = new VolatilityCriteria(10, 0.12, RelationalOperator._GT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<FundamentalPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setSecurityUniverse(securities);

		List<StockScreenPlan> plans = factory.build(criteria);

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(plans);
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	private List<HistoricalSecurity> createBasicHistoricalList() {
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
