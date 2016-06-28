package com.osprey.screen.criteria;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StockScreenCriteriaGenerator {

	private Map<String, Object> criteriaBucket;

	public StockScreenCriteriaGenerator() {

	}

	public StockScreenCriteriaGenerator(Map<String, Object> bucket) {
		this.criteriaBucket = bucket;
	}

	public IStockScreenCriteria generate() {
		ObjectMapper objectMapper = new ObjectMapper();

		IStockScreenCriteria result = null;
		switch (StockScreenType.valueOf((String) getCriteriaBucket().get("type"))) {
		case ADR:
			break;
		case ADX:
			break;
		case BETA:
			break;
		case BLOCK_TRADE_VOL:
			break;
		case BOLLINGER_BAND:
			break;
		case DEBT_TO_CAPITAL:
			break;
		case DMI:
			break;
		case EARNINGS_ANNOUNCEMENT:
			break;
		case EARNINGS_SUPRISE:
			break;
		case EMA:
			result = objectMapper.convertValue(getCriteriaBucket(), ExponentialMovingAverageCriteria.class);
			break;
		case EPS:
			break;
		case EXCHANGE:
			break;
		case GAP_UP_DOWN:
			break;
		case INDEX:
			break;
		case INSTITUTIONAL_HOLDINGS:
			break;
		case INSTRUMENT_TYPE:
			result = objectMapper.convertValue(getCriteriaBucket(), InstrumentTypeCriteria.class);
			break;
		case MACD_HISTOGRAM:
			break;
		case MARKET_CAP:
			break;
		case MOMENTUM_X:
			break;
		case NEW_52_WEEK:
			break;
		case ON_BALANCE_VOL:
			break;
		case PARABOLIC_SAR_X:
			break;
		case PE_RATIO:
			break;
		case PRICE:
			result = objectMapper.convertValue(getCriteriaBucket(), PreviousClosePriceCriteria.class);
			break;
		case PRICE_CHANGE:
			break;
		case PROFIT_MARGIN:
			break;
		case RATE_OF_CHANGE:
			break;
		case REVENUE_GROWTH:
			break;
		case ROE:
			break;
		case RSI:
			break;
		case SECTOR:
			break;
		case SMA:
			result = objectMapper.convertValue(getCriteriaBucket(), SimpleMovingAverageCriteria.class);
			break;
		case STOCHASTICS_X:
			break;
		case TODAY_VOL_VS_AVG:
			break;
		case VOLATILITY:
			break;
		case VOLUME_RANGE:
			break;
		case EMA_X:
			result = objectMapper.convertValue(getCriteriaBucket(), ExponentialMovingAverageCrossoverCriteria.class);
		default:
			break;

		}

		return result;
	}

	public Map<String, Object> getCriteriaBucket() {
		return criteriaBucket;
	}

	public void setCriteriaBucket(Map<String, Object> criteriaBucket) {
		this.criteriaBucket = criteriaBucket;
	}

}
