package com.osprey.screen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.osprey.math.OspreyJavaMath;
import com.osprey.screen.criteria.ExponentialMovingAverageCriteria;
import com.osprey.screen.criteria.IStockScreenCriteria;
import com.osprey.screen.criteria.PreviousClosePriceCriteria;
import com.osprey.screen.criteria.SimpleMovingAverageCriteria;
import com.osprey.screen.screens.ExponentialMovingAverageScreen;
import com.osprey.screen.screens.IStockScreen;
import com.osprey.screen.screens.PreviousClosePriceScreen;
import com.osprey.screen.screens.SimpleMovingAverageScreen;
import com.osprey.securitymaster.ExtendedPricedSecurity;
import com.osprey.securitymaster.HistoricalSecurity;

public class StockScreenPlanFactory {

	private Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities;
	private List<IStockScreenCriteria> criterias;

	public StockScreenPlanFactory() {

	}

	public StockScreenPlanFactory(Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities,
			List<IStockScreenCriteria> criteria) {
		this.criterias = criteria;
		this.securities = securities;
	}

	public void setSecurityUniverse(Map<ExtendedPricedSecurity, List<HistoricalSecurity>> securities) {
		this.securities = securities;
	}

	public void setCriteria(List<IStockScreenCriteria> criterias) {
		this.criterias = criterias;
	}

	public Map<Integer, StockScreenPlan> build() {
		Map<Integer, StockScreenPlan> plans = new HashMap<>(OspreyJavaMath.calcMapInitialSize(securities.size()));

		for (Entry<ExtendedPricedSecurity, List<HistoricalSecurity>> entry : securities.entrySet()) {
			StockScreenPlan plan = new StockScreenPlan(entry.getKey(), entry.getValue());
			plans.put(entry.getKey().getSecurityId(), plan);

			for (IStockScreenCriteria criteria : criterias) {
				plan.add(buildScreen(criteria));
			}

		}

		return plans;
	}

	public IStockScreen buildScreen(IStockScreenCriteria criteria) {

		switch (criteria.getType()) {
		case ADR:
		case ADX:
		case BETA:
		case BLOCK_TRADE_VOL:
		case BOLLINGER_BAND:
		case DEBT_TO_CAPITAL:
		case DMI:
		case EARNINGS_ANNOUNCEMENT:
		case EARNINGS_SUPRISE:
			return null;
		case EMA:
			return new ExponentialMovingAverageScreen((ExponentialMovingAverageCriteria) criteria);
		case EPS:
		case EXCHANGE:
		case GAP_UP_DOWN:
		case INDEX:
		case INSTITUTIONAL_HOLDINGS:
		case INSTRUMENT_TYPE:
		case MACD_HISTOGRAM:
		case MARKET_CAP:
		case MOMENTUM_X:
		case NEW_52_WEEK:
		case ON_BALANCE_VOL:
		case PARABOLIC_SAR_X:
		case PE_RATIO:
			return null;
		case PRICE:
			return new PreviousClosePriceScreen((PreviousClosePriceCriteria) criteria);
		case PRICE_CHANGE:
		case PROFIT_MARGIN:
		case RATE_OF_CHANGE:
		case REVENUE_GROWTH:
		case ROE:
		case RSI:
		case SECTOR:
			return null;
		case SMA:
			return new SimpleMovingAverageScreen((SimpleMovingAverageCriteria) criteria);
		case STOCHASTICS_X:
		case TODAY_VOL_VS_AVG:
		case VOLATILITY:
		case VOLUME_RANGE:
		default:
			break;

		}

		return null;
	}
}
