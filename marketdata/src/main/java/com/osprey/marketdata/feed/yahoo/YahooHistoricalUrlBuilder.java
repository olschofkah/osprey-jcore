package com.osprey.marketdata.feed.yahoo;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;

import com.osprey.marketdata.feed.constants.QuoteDataFrequency;

public class YahooHistoricalUrlBuilder {

	@Value("${yahoo.quote.stock.historical.url}")
	private String historicalQuoteUrl;

	@Value("${yahoo.quote.stock.historical.query.parameter.symbol}")
	private String symbolParameter;

	@Value("${yahoo.quote.stock.historical.query.parameter.begin.month}")
	private String beginMonthParameter;
	@Value("${yahoo.quote.stock.historical.query.parameter.begin.day}")
	private String beginDayParameter;
	@Value("${yahoo.quote.stock.historical.query.parameter.begin.year}")
	private String beginYearParameter;
	@Value("${yahoo.quote.stock.historical.query.parameter.end.month}")
	private String endMonthParameter;
	@Value("${yahoo.quote.stock.historical.query.parameter.end.day}")
	private String endDayParameter;
	@Value("${yahoo.quote.stock.historical.query.parameter.end.year}")
	private String endYearParameter;

	@Value("${yahoo.quote.stock.historical.query.parameter.frequency}")
	private String frequencyParameter;
	@Value("${yahoo.quote.stock.historical.query.parameter.frequency.day}")
	private String frequencyday;
	@Value("${yahoo.quote.stock.historical.query.parameter.frequency.week}")
	private String frequencyWeek;
	@Value("${yahoo.quote.stock.historical.query.parameter.frequency.month}")
	private String frequencyMonth;

	private String symbol;
	private LocalDate begin;
	private LocalDate end;
	private QuoteDataFrequency frequency;

	public YahooHistoricalUrlBuilder(String symbol, LocalDate begin, LocalDate end, QuoteDataFrequency frequency) {
		this.symbol = symbol;
		this.begin = begin;
		this.end = end;
		this.frequency = frequency;
	}

	public String build() {
		StringBuilder builder = new StringBuilder(historicalQuoteUrl);
		builder.append("?");
		builder.append(symbolParameter);
		builder.append("=");
		builder.append(symbol);
		builder.append("&");
		builder.append(beginMonthParameter);
		builder.append("=");
		builder.append(begin.getMonthValue() - 1); // 0 based
		builder.append("&");
		builder.append(beginDayParameter);
		builder.append("=");
		builder.append(begin.getDayOfMonth());
		builder.append("&");
		builder.append(beginYearParameter);
		builder.append("=");
		builder.append(begin.getYear());
		builder.append("&");
		builder.append(endMonthParameter);
		builder.append("=");
		builder.append(end.getMonthValue() - 1); // 0 based
		builder.append("&");
		builder.append(endDayParameter);
		builder.append("=");
		builder.append(end.getDayOfMonth());
		builder.append("&");
		builder.append(endYearParameter);
		builder.append("=");
		builder.append(end.getYear());
		builder.append("&");
		builder.append(frequencyParameter);
		builder.append("=");
		
		switch (frequency) {
		case DAY:
			builder.append(frequencyday);
			break;
		case WEEK:
			builder.append(frequencyWeek);
			break;
		case MONTH:
			builder.append(frequencyMonth);
			break;
		case YEAR:
		case HOUR:
		case MINUTE:
		case SECOND:
		case TICK:
		default:
			break;
		}

		return builder.toString();
	}

}
