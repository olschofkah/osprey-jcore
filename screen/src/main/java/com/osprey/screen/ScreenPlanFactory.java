package com.osprey.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;

import com.osprey.screen.criteria.BetaCriteria;
import com.osprey.screen.criteria.BollingerBandCrossCriteria;
import com.osprey.screen.criteria.BollingerBandLevelCriteria;
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
import com.osprey.screen.criteria.MoneyFlowIndexLevelCriteria;
import com.osprey.screen.criteria.MoneyFlowIndexLevelCrossCriteria;
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
import com.osprey.screen.screens.BetaScreen;
import com.osprey.screen.screens.BollingerBandCrossScreen;
import com.osprey.screen.screens.BollingerBandLevelScreen;
import com.osprey.screen.screens.DollarVolumeScreen;
import com.osprey.screen.screens.EarningsPercentMoveAverageScreen;
import com.osprey.screen.screens.EarningsScreen;
import com.osprey.screen.screens.EarningsVolatilityAverageScreen;
import com.osprey.screen.screens.ExponentialMovingAverageBandCrossoverScreen;
import com.osprey.screen.screens.ExponentialMovingAverageCrossoverScreen;
import com.osprey.screen.screens.ExponentialMovingAverageCurrentPriceCrossoverScreen;
import com.osprey.screen.screens.ExponentialMovingAverageScreen;
import com.osprey.screen.screens.ExponentialMovingAverageVsCurrentPriceScreen;
import com.osprey.screen.screens.IStockScreen;
import com.osprey.screen.screens.IndustryScreen;
import com.osprey.screen.screens.InstrumentTypeScreen;
import com.osprey.screen.screens.MarketCapScreen;
import com.osprey.screen.screens.MomentumScreen;
import com.osprey.screen.screens.MoneyFlowIndexLevelCrossScreen;
import com.osprey.screen.screens.MoneyFlowIndexLevelScreen;
import com.osprey.screen.screens.MovingAverageConverganceDiverganceCrossoverScreen;
import com.osprey.screen.screens.MovingAverageConverganceDiverganceDiverganceScreen;
import com.osprey.screen.screens.MovingAverageConverganceDiverganceLevelScreen;
import com.osprey.screen.screens.PreviousClosePriceScreen;
import com.osprey.screen.screens.PriceGapScreen;
import com.osprey.screen.screens.PricePercentageChangeScreen;
import com.osprey.screen.screens.RelativeStrengthIndexScreen;
import com.osprey.screen.screens.RotationIndicatorScreen;
import com.osprey.screen.screens.SectorScreen;
import com.osprey.screen.screens.SimpleMovingAverageScreen;
import com.osprey.screen.screens.StochasticFullOscillatorLevelCrossScreen;
import com.osprey.screen.screens.StochasticFullOscillatorLevelScreen;
import com.osprey.screen.screens.StochasticFullOscillatorSignalCrossScreen;
import com.osprey.screen.screens.SymbolScreen;
import com.osprey.screen.screens.VolatilityScreen;
import com.osprey.screen.screens.VolumeAverageComparisonScreen;
import com.osprey.screen.screens.VolumeAverageDeltaScreen;
import com.osprey.screen.screens.VolumeAverageScreen;
import com.osprey.screen.screens.VolumeScreen;
import com.osprey.screen.screens._52WeekRangePercentageScreen;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class ScreenPlanFactory {

	private Set<SecurityQuoteContainer> securities;

	public ScreenPlanFactory() {

	}

	public ScreenPlanFactory(Set<SecurityQuoteContainer> securities) {
		this.securities = securities;
	}

	public void setSecurityUniverse(Set<SecurityQuoteContainer> securities) {
		this.securities = securities;
	}

	/**
	 * Build a map of every security to the list of screens that it must pass.
	 * 
	 * @return Map of Security to it's screening plan.
	 */
	public List<ScreenPlan> build(List<IScreenCriteria> criterias) {
		List<ScreenPlan> plans = new ArrayList<>(securities.size());

		// flip the order since they're dropped onto a stack;
		// Collections.reverse(criterias);

		for (SecurityQuoteContainer sqc : securities) {
			ScreenPlan plan = new ScreenPlan(sqc);
			plans.add(plan);

			for (IScreenCriteria criteria : criterias) {
				plan.add(buildScreen(criteria));
			}
		}

		return plans;
	}

	public IStockScreen buildScreen(IScreenCriteria criteria) {

		switch (criteria.getType()) {
		case ADR:
			return null;
		case ADX:
			return null;
		case BETA:
			return new BetaScreen((BetaCriteria) criteria);
		case BLOCK_TRADE_VOL:
			return null;
		case BOLLINGER_BAND_X:
			return new BollingerBandCrossScreen((BollingerBandCrossCriteria) criteria);
		case BOLLINGER_BAND_LEVEL:
			return new BollingerBandLevelScreen((BollingerBandLevelCriteria) criteria);
		case DEBT_TO_CAPITAL:
			return null;
		case DMI:
			return null;
		case EARNINGS_ANNOUNCEMENT:
			return new EarningsScreen((EarningsCriteria) criteria);
		case ROTATION_INDICATOR:
			return new RotationIndicatorScreen((RotationIndicatorCriteria) criteria);
		case EARNINGS_SUPRISE:
			return null;
		case EMA:
			return new ExponentialMovingAverageScreen((ExponentialMovingAverageCriteria) criteria);
		case EMA_X:
			return new ExponentialMovingAverageCrossoverScreen((ExponentialMovingAverageCrossoverCriteria) criteria);
		case EPS:
			return null;
		case EXCHANGE:
			return null;
		case GAP_UP_DOWN:
			return new PriceGapScreen((PriceGapCriteria) criteria);
		case INDEX:
			return null;
		case INSTITUTIONAL_HOLDINGS:
			return null;
		case INSTRUMENT_TYPE:
			return new InstrumentTypeScreen((InstrumentTypeCriteria) criteria);
		case MACD:
			return new MovingAverageConverganceDiverganceCrossoverScreen(
					(MovingAverageConverganceDiverganceCrossoverCriteria) criteria);
		case MACD_DIVERGANCE:
			return new MovingAverageConverganceDiverganceDiverganceScreen(
					(MovingAverageConverganceDiverganceDiverganceCriteria) criteria);
		case MACD_LEVEL:
			return new MovingAverageConverganceDiverganceLevelScreen(
					(MovingAverageConverganceDiverganceLevelCriteria) criteria);
		case MARKET_CAP:
			return new MarketCapScreen((MarketCapCriteria) criteria);
		case MOMENTUM_X:
			return new MomentumScreen((MomentumCriteria) criteria);
		case NEW_52_WEEK:
			return null;
		case ON_BALANCE_VOL:
			return null;
		case PARABOLIC_SAR_X:
			return null;
		case PE_RATIO:
			return null;
		case PRICE:
			return new PreviousClosePriceScreen((PreviousClosePriceCriteria) criteria);
		case PRICE_CHANGE:
			return new PricePercentageChangeScreen((PricePercentageChangeCriteria) criteria);
		case PROFIT_MARGIN:
			return null;
		case RATE_OF_CHANGE:
			return null;
		case REVENUE_GROWTH:
			return null;
		case ROE:
			return null;
		case RSI:
			return new RelativeStrengthIndexScreen((RelativeStrengthIndexCriteria) criteria);
		case SECTOR:
			return new SectorScreen((SectorCriteria) criteria);
		case INDUSTRY:
			return new IndustryScreen((IndustryCriteria) criteria);
		case SMA:
			return new SimpleMovingAverageScreen((SimpleMovingAverageCriteria) criteria);
		case STOCHASTICS_FULL_SIGNAL_CROSS:
			return new StochasticFullOscillatorSignalCrossScreen(
					(StochasticFullOscillatorSignalCrossCriteria) criteria);
		case STOCHASTICS_FULL_LEVEL_CROSS:
			return new StochasticFullOscillatorLevelCrossScreen((StochasticFullOscillatorLevelCrossCriteria) criteria);
		case STOCHASTICS_FULL_LEVEL:
			return new StochasticFullOscillatorLevelScreen((StochasticFullOscillatorLevelCriteria) criteria);
		case VOLATILITY:
			return new VolatilityScreen((VolatilityCriteria) criteria);
		case VOLUME_AVG:
			return new VolumeAverageScreen((VolumeAverageCriteria) criteria);
		case VOLUME_AVG_DELTA:
			return new VolumeAverageDeltaScreen((VolumeAverageDeltaCriteria) criteria);
		case EMA_PCT_BANDS:
			return new ExponentialMovingAverageBandCrossoverScreen(
					(ExponentialMovingAverageBandCrossoverCriteria) criteria);
		case EMA_X_PRICE:
			return new ExponentialMovingAverageCurrentPriceCrossoverScreen(
					(ExponentialMovingAverageCurrentPriceCrossoverCriteria) criteria);
		case SYMBOL:
			return new SymbolScreen((SymbolCriteria) criteria);
		case VOLUME_COMPARISON:
			return new VolumeAverageComparisonScreen((VolumeAverageComparisonCriteria) criteria);
		case _52_PCT:
			return new _52WeekRangePercentageScreen((_52WeekRangePercentageCriteria) criteria);
		case EMA_VS_PRICE:
			return new ExponentialMovingAverageVsCurrentPriceScreen(
					(ExponentialMovingAverageVsCurrentPriceCriteria) criteria);
		case DOLLAR_VOLUME:
			return new DollarVolumeScreen((DollarVolumeCriteria) criteria);
		case VOLUME:
			return new VolumeScreen((VolumeCriteria) criteria);
		case EARNINGS_PCT_MOVE:
			return new EarningsPercentMoveAverageScreen((EarningsPercentMoveAverageCriteria) criteria);
		case EARNINGS_VOLATILITY:
			return new EarningsVolatilityAverageScreen((EarningsVolatilityAverageCriteria) criteria);
		case MFI:
			return new MoneyFlowIndexLevelScreen((MoneyFlowIndexLevelCriteria) criteria);
		case MFI_X:
			return new MoneyFlowIndexLevelCrossScreen((MoneyFlowIndexLevelCrossCriteria) criteria);
		default:
			break;

		}

		throw new NotImplementedException(
				String.format("Criteria %s not yet implemented in the factory!", criteria.getType()));
	}
}
