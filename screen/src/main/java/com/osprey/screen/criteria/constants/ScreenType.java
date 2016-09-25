package com.osprey.screen.criteria.constants;

public enum ScreenType {
	
	// basic
	EXCHANGE,
	INDEX,
	SECTOR,
	INDUSTRY,
	ADR,
	MARKET_CAP,
	BETA,
	VOLATILITY,
	INSTRUMENT_TYPE,
	SYMBOL,
	// Fundamentals
	REVENUE_GROWTH,
	EPS,
	ROE,
	PROFIT_MARGIN,
	EARNINGS_ANNOUNCEMENT,
	EARNINGS_VOLATILITY,
	EARNINGS_PCT_MOVE,
	EARNINGS_SUPRISE,
	INSTITUTIONAL_HOLDINGS,
	
	// Valuation
	DEBT_TO_CAPITAL,
	PE_RATIO,
	// ... 
	
	// Price & Volume
	PRICE,
	GAP_UP_DOWN,
	PRICE_CHANGE,
	NEW_52_WEEK,
	_52_PCT,
	VOLUME,
	DOLLAR_VOLUME,
	VOLUME_AVG,
	VOLUME_COMPARISON,
	BLOCK_TRADE_VOL,
	ON_BALANCE_VOL,
	
	// Technicals
	SMA,
	EMA,
	EMA_PCT_BANDS,
	EMA_X,
	EMA_X_PRICE,
	BOLLINGER_BAND_X,
	BOLLINGER_BAND_LEVEL,
	PARABOLIC_SAR_X,
	RSI,
	STOCHASTICS_FULL_SIGNAL_CROSS,
	STOCHASTICS_FULL_LEVEL_CROSS,
	STOCHASTICS_FULL_LEVEL,
	MOMENTUM_X,
	MACD,
	MACD_DIVERGANCE,
	MACD_LEVEL,
	RATE_OF_CHANGE,
	DMI,
	ADX, 
	EMA_VS_PRICE,
	VOLUME_AVG_DELTA,
	MFI,
	MFI_X,
	
	// Other
	ROTATION_INDICATOR, 
	CAMARILLA_BAND,
	CAMARILLA_BAND_OPEN;
	
}
