package com.osprey.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.Assert;

import com.osprey.screen.criteria.IStockScreenCriteria;
import com.osprey.screen.criteria.PreviousClosePriceCriteria;
import com.osprey.screen.criteria.RelationalOperator;
import com.osprey.screen.criteria.SimpleMovingAverageCriteria;
import com.osprey.securitymaster.ExtendedPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;

public class BasicScreenPlanTest {

	private static String TEST_TICKER_1 = "QQQ";

	@Test
	public void testSinglePriceScreen1() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		ExtendedPricedSecurity s = new ExtendedPricedSecurity();
		s.setTicker(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(5, RelationalOperator._GT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setCriteria(criteria);
		factory.setSecurityUniverse(securities);

		Map<String, StockScreenPlan> planMap = factory.build();

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(planMap.values());
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSinglePriceScreen2() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		ExtendedPricedSecurity s = new ExtendedPricedSecurity();
		s.setTicker(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(500, RelationalOperator._GT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setCriteria(criteria);
		factory.setSecurityUniverse(securities);

		Map<String, StockScreenPlan> planMap = factory.build();

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(planMap.values());
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertFalse(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSinglePriceScreen3() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		ExtendedPricedSecurity s = new ExtendedPricedSecurity();
		s.setTicker(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(25, RelationalOperator._EQ);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setCriteria(criteria);
		factory.setSecurityUniverse(securities);

		Map<String, StockScreenPlan> planMap = factory.build();

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(planMap.values());
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSinglePriceScreen4() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		ExtendedPricedSecurity s = new ExtendedPricedSecurity();
		s.setTicker(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(5, RelationalOperator._GE);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setCriteria(criteria);
		factory.setSecurityUniverse(securities);

		Map<String, StockScreenPlan> planMap = factory.build();

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(planMap.values());
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSinglePriceScreen5() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		ExtendedPricedSecurity s = new ExtendedPricedSecurity();
		s.setTicker(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(35, RelationalOperator._LT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setCriteria(criteria);
		factory.setSecurityUniverse(securities);

		Map<String, StockScreenPlan> planMap = factory.build();

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(planMap.values());
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSinglePriceScreen6() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		ExtendedPricedSecurity s = new ExtendedPricedSecurity();
		s.setTicker(TEST_TICKER_1);
		s.setClose(25);

		PreviousClosePriceCriteria c1 = new PreviousClosePriceCriteria(25.001, RelationalOperator._LE);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setCriteria(criteria);
		factory.setSecurityUniverse(securities);

		Map<String, StockScreenPlan> planMap = factory.build();

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(planMap.values());
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}

	@Test
	public void testSingleSMAScreen1() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		ExtendedPricedSecurity s = new ExtendedPricedSecurity();
		s.setTicker(TEST_TICKER_1);
		s.setClose(closingPrices.get(closingPrices.size() - 1).getClose());

		SimpleMovingAverageCriteria c1 = new SimpleMovingAverageCriteria(4, 8, RelationalOperator._GT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);

		Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setCriteria(criteria);
		factory.setSecurityUniverse(securities);

		Map<String, StockScreenPlan> planMap = factory.build();

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(planMap.values());
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}
	
	@Test
	public void testSingleSMAAndPriceScreen1() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		ExtendedPricedSecurity s = new ExtendedPricedSecurity();
		s.setTicker(TEST_TICKER_1);
		s.setClose(closingPrices.get(closingPrices.size() - 1).getClose());

		SimpleMovingAverageCriteria c1 = new SimpleMovingAverageCriteria(4, 8, RelationalOperator._GT);
		PreviousClosePriceCriteria c2 = new PreviousClosePriceCriteria(25.00, RelationalOperator._GT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);
		criteria.add(c2);

		Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setCriteria(criteria);
		factory.setSecurityUniverse(securities);

		Map<String, StockScreenPlan> planMap = factory.build();

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(planMap.values());
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertTrue(resultSet.contains(TEST_TICKER_1));
	}
	
	@Test
	public void testSingleSMAAndPriceScreen2() throws Exception {

		List<HistoricalSecurity> closingPrices = createBasicHistoricalList();

		ExtendedPricedSecurity s = new ExtendedPricedSecurity();
		s.setTicker(TEST_TICKER_1);
		s.setClose(closingPrices.get(closingPrices.size() - 1).getClose());

		SimpleMovingAverageCriteria c1 = new SimpleMovingAverageCriteria(4, 8, RelationalOperator._GT);
		PreviousClosePriceCriteria c2 = new PreviousClosePriceCriteria(55.00, RelationalOperator._GT);

		List<IStockScreenCriteria> criteria = new ArrayList<>();
		criteria.add(c1);
		criteria.add(c2);

		Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities = new HashMap<>();
		securities.put(s, closingPrices);

		StockScreenPlanFactory factory = new StockScreenPlanFactory();
		factory.setCriteria(criteria);
		factory.setSecurityUniverse(securities);

		Map<String, StockScreenPlan> planMap = factory.build();

		BasicStockScreenExecutor executor = new BasicStockScreenExecutor();
		executor.setPlans(planMap.values());
		executor.execute();

		Set<String> resultSet = executor.getResultSet();

		Assert.assertFalse(resultSet.contains(TEST_TICKER_1));
	}

	private List<HistoricalSecurity> createBasicHistoricalList() {
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
		return closingPrices;
	}

}
