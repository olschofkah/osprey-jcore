package com.osprey.marketdata.feed;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.osprey.marketdata.feed.constants.QuoteDataFrequency;
import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.Security;

public interface IHistoricalQuoteSerice {

	public List<HistoricalQuote> quoteHistorical(Security s, LocalDate start, LocalDate end,
			QuoteDataFrequency frequency) throws MarketDataNotAvailableException, MarketDataIOException;

	public Map<Security, List<HistoricalQuote>> quoteHistoricalBatch(Set<Security> s, LocalDate start, LocalDate end)
			throws MarketDataNotAvailableException, MarketDataIOException;

}
