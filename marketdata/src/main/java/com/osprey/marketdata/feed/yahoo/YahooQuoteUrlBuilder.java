package com.osprey.marketdata.feed.yahoo;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.beans.factory.annotation.Value;

@NotThreadSafe
public class YahooQuoteUrlBuilder {

	@Value("${yahoo.quote.stock.url}")
	private String yahooQuoteUrl;

	@Value("${yahoo.quote.stock.module.summaryProfile}")
	private String summaryProfile;
	@Value("${yahoo.quote.stock.module.summaryDetail}")
	private String summaryDetail;
	@Value("${yahoo.quote.stock.module.financialData}")
	private String financialData;
	@Value("${yahoo.quote.stock.module.recommendationTrend}")
	private String recommendationTrend;
	@Value("${yahoo.quote.stock.module.upgradeDowngradeHistory}")
	private String upgradeDowngradeHistory;
	@Value("${yahoo.quote.stock.module.earnings}")
	private String earnings;
	@Value("${yahoo.quote.stock.module.defaultKeyStatistics}")
	private String defaultKeyStatistics;
	@Value("${yahoo.quote.stock.module.calendarEvents}")
	private String calendarEvents;
	@Value("${yahoo.quote.stock.module.price}")
	private String price;

	@Value("${yahoo.quote.stock.module.seperator}")
	private String seperator;
	@Value("${yahoo.quote.stock.module.fieldName}")
	private String fieldName;

	private final StringBuilder urlBuilder;
	private final String symbol;

	private int moduleCount = 0;

	public YahooQuoteUrlBuilder(String symbol) {
		this.symbol = symbol;
		this.urlBuilder = new StringBuilder();
	}

	public String build() {
		if (moduleCount == 0) {
			throw new RuntimeException("Must supply at least one module.");
		}

		return yahooQuoteUrl + symbol + "?" + fieldName + "=" + urlBuilder.toString();
	}

	public YahooQuoteUrlBuilder price() {
		prepend();
		urlBuilder.append(price);
		return this;
	}
	
	public YahooQuoteUrlBuilder summaryProfile() {
		prepend();
		urlBuilder.append(summaryProfile);
		return this;
	}

	public YahooQuoteUrlBuilder summaryDetail() {
		prepend();
		urlBuilder.append(summaryDetail);
		return this;
	}

	public YahooQuoteUrlBuilder financialData() {
		prepend();
		urlBuilder.append(financialData);
		return this;
	}

	public YahooQuoteUrlBuilder recommendationTrend() {
		prepend();
		urlBuilder.append(recommendationTrend);
		return this;
	}

	public YahooQuoteUrlBuilder upgradeDowngradeHistory() {
		prepend();
		urlBuilder.append(upgradeDowngradeHistory);
		return this;
	}

	public YahooQuoteUrlBuilder earnings() {
		prepend();
		urlBuilder.append(earnings);
		return this;
	}

	public YahooQuoteUrlBuilder defaultKeyStatistics() {
		prepend();
		urlBuilder.append(defaultKeyStatistics);
		return this;
	}

	public YahooQuoteUrlBuilder calendarEvents() {
		prepend();
		urlBuilder.append(calendarEvents);
		return this;
	}

	private void prepend() {
		if (moduleCount != 0) {
			urlBuilder.append(seperator);
		}
		++moduleCount;
	}

}
