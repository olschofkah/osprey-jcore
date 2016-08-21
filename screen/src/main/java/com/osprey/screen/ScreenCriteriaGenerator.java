package com.osprey.screen;

import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osprey.screen.criteria.BetaCriteria;
import com.osprey.screen.criteria.DollarVolumeCriteria;
import com.osprey.screen.criteria.EarningsCriteria;
import com.osprey.screen.criteria.EarningsPercentMoveAverageCriteria;
import com.osprey.screen.criteria.EarningsVolatilityAverageCriteria;
import com.osprey.screen.criteria.ExponentialMovingAverageBandCrossoverCriteria;
import com.osprey.screen.criteria.ExponentialMovingAverageCriteria;
import com.osprey.screen.criteria.ExponentialMovingAverageCrossoverCriteria;
import com.osprey.screen.criteria.ExponentialMovingAverageCurrentPriceCrossoverCriteria;
import com.osprey.screen.criteria.ExponentialMovingAverageVsCurrentPriceCriteria;
import com.osprey.screen.criteria.IScreenCriteria;
import com.osprey.screen.criteria.IndustryCriteria;
import com.osprey.screen.criteria.InstrumentTypeCriteria;
import com.osprey.screen.criteria.MarketCapCriteria;
import com.osprey.screen.criteria.MomentumCriteria;
import com.osprey.screen.criteria.MovingAverageConverganceDiverganceCrossoverCriteria;
import com.osprey.screen.criteria.MovingAverageConverganceDiverganceDiverganceCriteria;
import com.osprey.screen.criteria.MovingAverageConverganceDiverganceLevelCriteria;
import com.osprey.screen.criteria.PreviousClosePriceCriteria;
import com.osprey.screen.criteria.PriceGapCriteria;
import com.osprey.screen.criteria.PricePercentageChangeCriteria;
import com.osprey.screen.criteria.RelativeStrengthIndexCriteria;
import com.osprey.screen.criteria.RotationIndicatorCriteria;
import com.osprey.screen.criteria.SectorCriteria;
import com.osprey.screen.criteria.SimpleMovingAverageCriteria;
import com.osprey.screen.criteria.StochasticFullOscillatorLevelCriteria;
import com.osprey.screen.criteria.StochasticFullOscillatorLevelCrossCriteria;
import com.osprey.screen.criteria.StochasticFullOscillatorSignalCrossCriteria;
import com.osprey.screen.criteria.SymbolCriteria;
import com.osprey.screen.criteria.VolatilityCriteria;
import com.osprey.screen.criteria.VolumeAverageComparisonCriteria;
import com.osprey.screen.criteria.VolumeAverageCriteria;
import com.osprey.screen.criteria.VolumeAverageDeltaCriteria;
import com.osprey.screen.criteria.VolumeCriteria;
import com.osprey.screen.criteria._52WeekRangePercentageCriteria;
import com.osprey.screen.criteria.constants.ScreenType;

public class ScreenCriteriaGenerator {

	private Map<String, Object> criteriaBucket;

	public ScreenCriteriaGenerator() {

	}

	public ScreenCriteriaGenerator(Map<String, Object> bucket) {
		this.criteriaBucket = bucket;
	}

	public IScreenCriteria generate() {
		ObjectMapper objectMapper = new ObjectMapper();

		IScreenCriteria result = null;
		switch (ScreenType.valueOf((String) getCriteriaBucket().get("type"))) {
		case ADR:
			break;
		case ADX:
			break;
		case BETA:
			result = objectMapper.convertValue(getCriteriaBucket(), BetaCriteria.class);
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
			result = objectMapper.convertValue(getCriteriaBucket(), EarningsCriteria.class);
			break;
		case ROTATION_INDICATOR:
			result = objectMapper.convertValue(getCriteriaBucket(), RotationIndicatorCriteria.class);
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
			result = objectMapper.convertValue(getCriteriaBucket(), PriceGapCriteria.class);
			break;
		case INDEX:
			break;
		case INSTITUTIONAL_HOLDINGS:
			break;
		case INSTRUMENT_TYPE:
			result = objectMapper.convertValue(getCriteriaBucket(), InstrumentTypeCriteria.class);
			break;
		case MACD:
			result = objectMapper.convertValue(getCriteriaBucket(),
					MovingAverageConverganceDiverganceCrossoverCriteria.class);
			break;
		case MACD_DIVERGANCE:
			result = objectMapper.convertValue(getCriteriaBucket(),
					MovingAverageConverganceDiverganceDiverganceCriteria.class);
			break;
		case MACD_LEVEL:
			result = objectMapper.convertValue(getCriteriaBucket(),
					MovingAverageConverganceDiverganceLevelCriteria.class);
			break;
		case MARKET_CAP:
			result = objectMapper.convertValue(getCriteriaBucket(), MarketCapCriteria.class);
			break;
		case MOMENTUM_X:
			result = objectMapper.convertValue(getCriteriaBucket(), MomentumCriteria.class);
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
			result = objectMapper.convertValue(getCriteriaBucket(), PricePercentageChangeCriteria.class);
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
			result = objectMapper.convertValue(getCriteriaBucket(), RelativeStrengthIndexCriteria.class);
			break;
		case SECTOR:
			result = objectMapper.convertValue(getCriteriaBucket(), SectorCriteria.class);
			break;
		case INDUSTRY:
			result = objectMapper.convertValue(getCriteriaBucket(), IndustryCriteria.class);
			break;
		case SMA:
			result = objectMapper.convertValue(getCriteriaBucket(), SimpleMovingAverageCriteria.class);
			break;
		case STOCHASTICS_FULL_SIGNAL_CROSS:
			result = objectMapper.convertValue(getCriteriaBucket(), StochasticFullOscillatorSignalCrossCriteria.class);
			break;
		case STOCHASTICS_FULL_LEVEL_CROSS:
			result = objectMapper.convertValue(getCriteriaBucket(), StochasticFullOscillatorLevelCrossCriteria.class);
			break;
		case STOCHASTICS_FULL_LEVEL:
			result = objectMapper.convertValue(getCriteriaBucket(), StochasticFullOscillatorLevelCriteria.class);
			break;
		case VOLATILITY:
			result = objectMapper.convertValue(getCriteriaBucket(), VolatilityCriteria.class);
			break;
		case VOLUME_AVG:
			result = objectMapper.convertValue(getCriteriaBucket(), VolumeAverageCriteria.class);
			break;
		case VOLUME_AVG_DELTA:
			result = objectMapper.convertValue(getCriteriaBucket(), VolumeAverageDeltaCriteria.class);
			break;
		case EMA_X:
			result = objectMapper.convertValue(getCriteriaBucket(), ExponentialMovingAverageCrossoverCriteria.class);
			break;
		case EMA_PCT_BANDS:
			result = objectMapper.convertValue(getCriteriaBucket(),
					ExponentialMovingAverageBandCrossoverCriteria.class);
			break;
		case EMA_X_PRICE:
			result = objectMapper.convertValue(getCriteriaBucket(),
					ExponentialMovingAverageCurrentPriceCrossoverCriteria.class);
			break;
		case SYMBOL:
			result = objectMapper.convertValue(getCriteriaBucket(), SymbolCriteria.class);
			break;
		case VOLUME_COMPARISON:
			result = objectMapper.convertValue(getCriteriaBucket(), VolumeAverageComparisonCriteria.class);
			break;
		case _52_PCT:
			result = objectMapper.convertValue(getCriteriaBucket(), _52WeekRangePercentageCriteria.class);
			break;
		case EMA_VS_PRICE:
			result = objectMapper.convertValue(getCriteriaBucket(),
					ExponentialMovingAverageVsCurrentPriceCriteria.class);
			break;
		case DOLLAR_VOLUME:
			result = objectMapper.convertValue(getCriteriaBucket(), DollarVolumeCriteria.class);
			break;
		case VOLUME:
			result = objectMapper.convertValue(getCriteriaBucket(), VolumeCriteria.class);
			break;
		case EARNINGS_PCT_MOVE:
			result = objectMapper.convertValue(getCriteriaBucket(), EarningsPercentMoveAverageCriteria.class);
			break;
		case EARNINGS_VOLATILITY:
			result = objectMapper.convertValue(getCriteriaBucket(), EarningsVolatilityAverageCriteria.class);
			break;
		default:
			break;

		}

		if (result == null) {
			throw new NotImplementedException(String.format("Criteria %s not yet implemented in the generator!",
					ScreenType.valueOf((String) getCriteriaBucket().get("type")).toString()));
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
