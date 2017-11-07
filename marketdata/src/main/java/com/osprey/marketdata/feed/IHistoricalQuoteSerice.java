package com.osprey.marketdata.feed;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.osprey.marketdata.feed.constants.QuoteDataFrequency;
import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityKey;

public interface IHistoricalQuoteSerice {

	public List<HistoricalQuote> quoteHistorical(SecurityKey s, LocalDate start, LocalDate end,
			QuoteDataFrequency frequency) throws MarketDataNotAvailableException, MarketDataIOException;

	public Map<SecurityKey, List<HistoricalQuote>> quoteHistoricalBatch(Set<SecurityKey> s, LocalDate start, LocalDate end)
			throws MarketDataNotAvailableException, MarketDataIOException;

}
