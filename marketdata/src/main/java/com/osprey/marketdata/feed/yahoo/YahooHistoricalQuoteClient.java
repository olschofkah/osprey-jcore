package com.osprey.marketdata.feed.yahoo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.osprey.marketdata.feed.IHistoricalQuoteSerice;
import com.osprey.marketdata.feed.constants.QuoteDataFrequency;
import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.securitymaster.HistoricalQuote;
import com.osprey.securitymaster.SecurityKey;
import com.osprey.securitymaster.utils.OspreyUtils;

public class YahooHistoricalQuoteClient implements IHistoricalQuoteSerice {

	final static Logger logger = LogManager.getLogger(YahooHistoricalQuoteClient.class);

	@Autowired
	private ApplicationContext appCtx;

	// TODO add symbology translator at the quote level

	@Override
	public List<HistoricalQuote> quoteHistorical(SecurityKey s, LocalDate start, LocalDate end,
			QuoteDataFrequency frequency) throws MarketDataNotAvailableException, MarketDataIOException {

		String historicalQuoteUrl = appCtx
				.getBean(YahooHistoricalUrlBuilder.class, s.getSymbol(), start, end, frequency).build();

		logger.info("Quoting Historical Prices for {} @ {}", new Object[] { s.getSymbol(), historicalQuoteUrl });

		List<String> lines = extractUrlToList(historicalQuoteUrl);

		return parse(s.getSymbol(), lines);
	}

	private List<HistoricalQuote> parse(String symbol, List<String> lines) {

		List<HistoricalQuote> history = new ArrayList<>(lines.size());
		ZonedDateTime ts = ZonedDateTime.now();
		for (int i = 1; i < lines.size(); ++i) {
			history.add(parse(symbol, lines.get(i), ts));
		}

		return history;
	}

	private HistoricalQuote parse(String symbol, String line, ZonedDateTime ts) {

		// Date Open High Low Close Volume Adj Close
		// 6/22/2016 114.650002 114.739998 113.610001 113.910004 14751100
		// 113.910004

		String[] s = line.split(",");

		HistoricalQuote hq = new HistoricalQuote(symbol, LocalDate.parse(s[0]));
		hq.setAdjClose(Double.parseDouble(s[6]));
		hq.setClose(Double.parseDouble(s[4]));
		hq.setHigh(Double.parseDouble(s[2]));
		hq.setLow(Double.parseDouble(s[3]));
		hq.setOpen(Double.parseDouble(s[1]));
		hq.setVolume(Long.parseLong(s[5]));
		hq.setTimestamp(ts);

		return hq;
	}

	private List<String> extractUrlToList(String historicalQuoteUrl)
			throws MarketDataNotAvailableException, MarketDataIOException {

		try {
			// TODO Extract constants
			return OspreyUtils.readLinesFromUrl(historicalQuoteUrl, 30000, 30000);
		} catch (FileNotFoundException e) {
			throw new MarketDataNotAvailableException("", e);
		} catch (IOException e) {
			throw new MarketDataIOException("", e);
		}

	}

	@Override
	public Map<SecurityKey, List<HistoricalQuote>> quoteHistoricalBatch(Set<SecurityKey> s, LocalDate start,
			LocalDate end) {
		throw new NotImplementedException(
				"fetchHistoricalBatch(Set<Security>, LocalDate, LocalDate) is not implemented for YahooHistoricalQuoteClient");
	}

}
