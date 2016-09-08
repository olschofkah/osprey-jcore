package com.osprey.marketdata.feed.ychart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.osprey.marketdata.feed.exception.MarketDataIOException;
import com.osprey.marketdata.feed.exception.MarketDataNotAvailableException;
import com.osprey.marketdata.feed.ychart.pojo.Events;
import com.osprey.securitymaster.SecurityQuoteContainer;

public class YChartHistoricalEventsClient {

	private final static Logger logger = LogManager.getLogger(YChartHistoricalEventsClient.class);

	@Value("${ychart.quote.stock.historical.events.url}")
	private String yChartUrl;

	private RestTemplate http;

	public YChartHistoricalEventsClient(RestTemplate http) {
		this.http = http;
	}

	public SecurityQuoteContainer populateEvents(SecurityQuoteContainer sqc)
			throws MarketDataNotAvailableException, MarketDataIOException {
		logger.info("Finding events for {} ... ", () -> sqc.getKey().getSymbol());

		String url = yChartUrl + sqc.getKey().getSymbol();

		Events events = null;
		try {
			events = http.getForObject(url, Events.class);
		} catch (HttpClientErrorException e1) {
			if (e1.getMessage().contains("404 Not Found")) {
				throw new MarketDataNotAvailableException(
						"404 when quoting " + sqc.getKey().getSymbol() + " Message: " + e1.getMessage());
			} else {
				throw new MarketDataIOException(e1);
			}
		} catch (HttpMessageNotReadableException | HttpServerErrorException | ResourceAccessException e2) {
			throw new MarketDataIOException(e2);
		}

		logger.debug("Completed pulling events for {} ... ", () -> sqc.getKey().getSymbol());

		return YChartEventsResultMapper.map(events, sqc);
	}

}
