package com.osprey.marketdata.feed.yahoo;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.marketdata.feed.yahoo.pojo.CalendarEvents;
import com.osprey.marketdata.feed.yahoo.pojo.DefaultKeyStatistics;
import com.osprey.marketdata.feed.yahoo.pojo.Earnings_;
import com.osprey.marketdata.feed.yahoo.pojo.FinancialData;
import com.osprey.marketdata.feed.yahoo.pojo.Price;
import com.osprey.marketdata.feed.yahoo.pojo.Quarterly;
import com.osprey.marketdata.feed.yahoo.pojo.Quarterly_;
import com.osprey.marketdata.feed.yahoo.pojo.Result;
import com.osprey.marketdata.feed.yahoo.pojo.SummaryDetail;
import com.osprey.marketdata.feed.yahoo.pojo.SummaryProfile;
import com.osprey.securitymaster.FundamentalQuote;
import com.osprey.securitymaster.Security;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityQuote;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.SecurityUpcomingEvents;
import com.osprey.securitymaster.constants.SecurityEventType;
import com.osprey.securitymaster.utils.OspreyUtils;

public class YahooQuoteResultMapper {

	final static Logger logger = LogManager.getLogger(YahooQuoteResultMapper.class);

	public static SecurityQuoteContainer map(Result result, SecurityQuoteContainer sqc) {

		if (result.getCalendarEvents() != null) {
			mapCalendarEvents(result.getCalendarEvents(), sqc);
		}

		if (result.getDefaultKeyStatistics() != null) {
			mapDefaultKeyStatistics(result.getDefaultKeyStatistics(), sqc);
		}

		if (result.getEarnings() != null) {
			mapEarnings(result.getEarnings(), sqc);
		}

		if (result.getFinancialData() != null) {
			mapFinancialData(result.getFinancialData(), sqc);
		}

		if (result.getSummaryDetail() != null) {
			mapSummaryDetail(result.getSummaryDetail(), sqc);
		}

		if (result.getSummaryProfile() != null) {
			mapSummaryProfile(result.getSummaryProfile(), sqc);
		}

		// this should be last as it is more live data and will override a few
		// data points.
		if (result.getPrice() != null) {
			mapPrice(result.getPrice(), sqc);
		}

		sqc.timestamp();

		return sqc;
	}

	// TODO consider pre-market & post-market quotes

	private static void mapPrice(Price p, SecurityQuoteContainer sqc) {

		SecurityQuote s;
		if (sqc.getSecurityQuote() == null) {
			s = new SecurityQuote(sqc.getKey());
			sqc.setSecurityQuote(s);
		} else {
			s = sqc.getSecurityQuote();
		}

		if (p.getRegularMarketPrice() != null && p.getRegularMarketPrice().getRaw() != null) {
			s.setLast(p.getRegularMarketPrice().getRaw());
		}

		// TODO Need p.getOpenInterest() for equity quote?

		if (p.getRegularMarketSource() != null) {
			s.setDataCurrency(p.getRegularMarketSource());
		}
		if (p.getRegularMarketDayHigh() != null && p.getRegularMarketDayHigh().getRaw() != null) {
			s.setHigh(p.getRegularMarketDayHigh().getRaw());
		}
		if (p.getRegularMarketDayLow() != null && p.getRegularMarketDayLow().getRaw() != null) {
			s.setLow(p.getRegularMarketDayLow().getRaw());
		}
		if (p.getRegularMarketOpen() != null && p.getRegularMarketOpen().getRaw() != null) {
			s.setOpen(p.getRegularMarketOpen().getRaw());
		}
		if (p.getRegularMarketVolume() != null && p.getRegularMarketVolume().getRaw() != null) {
			s.setVolume(p.getRegularMarketVolume().getRaw());
		}
		if (p.getRegularMarketPrice() != null && p.getRegularMarketPrice().getRaw() != null) {
			s.setLast(p.getRegularMarketPrice().getRaw());
		}

		// Updates to Security
		if (sqc.getSecurity() != null) {
			if (p.getCurrency() != null) {
				sqc.getSecurity().setCurrency(p.getCurrency());
			}
			if (p.getRegularMarketPreviousClose() != null && p.getRegularMarketPreviousClose().getRaw() != null) {
				sqc.getSecurity().setPreviousClose(p.getRegularMarketPreviousClose().getRaw());
			}
			if (p.getLongName() != null) {
				sqc.getSecurity().setCompanyName(p.getLongName());
			}
		}
	}

	private static void mapSummaryProfile(SummaryProfile sp, SecurityQuoteContainer sqc) {

		if (sqc.getSecurity() != null) {
			Security s = sqc.getSecurity();
			if (sp.getCountry() != null) {
				s.setCountry(sp.getCountry());
			}
			if (sp.getIndustry() != null) {
				s.setIndustry(sp.getIndustry());
			}
			if (sp.getLongBusinessSummary() != null) {
				s.setCompanyDescription(sp.getLongBusinessSummary());
			}
			if (sp.getSector() != null) {
				s.setSector(sp.getSector());
			}
			if (sp.getState() != null) {
				s.setState(sp.getState());
			}
			if (sp.getFullTimeEmployees() != null) {
				s.setEmployeeCount(sp.getFullTimeEmployees());
			}
		}
	}

	private static void mapSummaryDetail(SummaryDetail sd, SecurityQuoteContainer sqc) {

		SecurityQuote quote;
		if (sqc.getSecurityQuote() == null) {
			quote = new SecurityQuote(sqc.getKey());
			sqc.setSecurityQuote(quote);
		} else {
			quote = sqc.getSecurityQuote();
		}

		// updates to the quote
		if (sd.getAsk() != null && sd.getAsk().getRaw() != null) {
			quote.setAsk(sd.getAsk().getRaw());
		}

		if (sd.getBid() != null && sd.getBid().getRaw() != null) {
			quote.setBid(sd.getBid().getRaw());
		}

		if (sd.getBidSize() != null && sd.getBidSize().getRaw() != null) {
			quote.setBidSize(sd.getBidSize().getRaw());
		}

		if (sd.getAskSize() != null && sd.getAskSize().getRaw() != null) {
			quote.setAskSize(sd.getAskSize().getRaw());
		}

		if (sd.getOpen() != null && sd.getOpen().getRaw() != null) {
			quote.setOpen(sd.getOpen().getRaw());
		}

		if (sd.getDayHigh() != null && sd.getDayHigh().getRaw() != null) {
			quote.setHigh(sd.getDayHigh().getRaw());
		}

		if (sd.getDayLow() != null && sd.getDayLow().getRaw() != null) {
			quote.setLow(sd.getDayLow().getRaw());
		}

		// Updates to Security
		if (sqc.getSecurity() != null) {
			if (sd.getPreviousClose() != null && sd.getPreviousClose().getRaw() != null) {
				sqc.getSecurity().setPreviousClose(sd.getPreviousClose().getRaw());
			}
		}

		// fundamental quote info
		FundamentalQuote fq;
		if (sqc.getFundamentalQuote() == null) {
			fq = new FundamentalQuote(sqc.getKey(), LocalDate.now());
			sqc.setFundamentalQuote(fq);
		} else {
			fq = sqc.getFundamentalQuote();
		}

		if (sd.getAverageVolume() != null && sd.getAverageVolume().getRaw() != null) {
			fq.setAverageVolume(sd.getAverageVolume().getRaw());
		}

		if (sd.getFiftyTwoWeekHigh() != null && sd.getFiftyTwoWeekHigh().getRaw() != null) {
			fq.set_52WeekHigh(sd.getFiftyTwoWeekHigh().getRaw());
		}
		
		if (sd.getFiftyTwoWeekLow() != null && sd.getFiftyTwoWeekLow().getRaw() != null) {
			fq.set_52WeekLow(sd.getFiftyTwoWeekLow().getRaw());
		}

		// TODO set the other shit for fundamental quote
	}

	private static void mapFinancialData(FinancialData fd, SecurityQuoteContainer sqc) {

		// fundamental quote info
		FundamentalQuote fq;
		if (sqc.getFundamentalQuote() == null) {
			fq = new FundamentalQuote(sqc.getKey(), LocalDate.now());
			sqc.setFundamentalQuote(fq);
		} else {
			fq = sqc.getFundamentalQuote();
		}

		if (fd.getDebtToEquity() != null && fd.getDebtToEquity().getRaw() != null) {
			fq.setDebtToEquity(fd.getDebtToEquity().getRaw());
		}
		if (fd.getReturnOnAssets() != null && fd.getReturnOnAssets().getRaw() != null) {
			fq.setReturnOnAssets(fd.getReturnOnAssets().getRaw());
		}
		if (fd.getReturnOnEquity() != null && fd.getReturnOnEquity().getRaw() != null) {
			fq.setReturnOnEquity(fd.getReturnOnEquity().getRaw());
		}
		if (fd.getRevenuePerShare() != null && fd.getRevenuePerShare().getRaw() != null) {
			fq.setRevenuePerShare(fd.getRevenuePerShare().getRaw());
		}
		if (fd.getRevenueGrowth() != null && fd.getRevenueGrowth().getRaw() != null) {
			fq.setRevenueGrowth(fd.getRevenueGrowth().getRaw());
		}

		// TODO set the other shit for fundamental quote

	}

	private static void mapEarnings(Earnings_ earnings, SecurityQuoteContainer sqc) {

		List<SecurityEvent> events;
		if (sqc.getEvents() == null) {
			events = new ArrayList<SecurityEvent>();
			sqc.setEvents(events);
		} else {
			events = sqc.getEvents();
		}

		ZonedDateTime now = ZonedDateTime.now();

		for (Quarterly quarterly : earnings.getEarningsChart().getQuarterly()) {
			events.add(
					new SecurityEvent(sqc.getKey(), parseEventDate(quarterly.getDate()), SecurityEventType.EARNINGS_ACT,
							quarterly.getActual() == null ? 0.0 : quarterly.getActual().getRaw(), now));
			events.add(
					new SecurityEvent(sqc.getKey(), parseEventDate(quarterly.getDate()), SecurityEventType.EARNINGS_EST,
							quarterly.getEstimate() == null ? 0.0 : quarterly.getEstimate().getRaw(), now));
		}

		for (Quarterly_ quarterly : earnings.getFinancialsChart().getQuarterly()) {
			events.add(new SecurityEvent(sqc.getKey(), parseEventDate(quarterly.getDate()), SecurityEventType.REVENUE,
					quarterly.getRevenue() == null ? 0.0 : quarterly.getRevenue().getRaw(), now));
		}
	}

	private static LocalDate parseEventDate(String date) {
		logger.trace("Parsing event date {} ", () -> date);

		String quarter = StringUtils.substring(date, 0, 2);
		String year = StringUtils.substring(date, 2, 6);

		switch (quarter) {
		case "1Q":
			return LocalDate.of(Integer.parseInt(year), 1, 1);
		case "2Q":
			return LocalDate.of(Integer.parseInt(year), 4, 1);
		case "3Q":
			return LocalDate.of(Integer.parseInt(year), 7, 1);
		case "4Q":
			return LocalDate.of(Integer.parseInt(year), 10, 1);
		default:
			return null;
		}
	}

	private static void mapDefaultKeyStatistics(DefaultKeyStatistics stats, SecurityQuoteContainer sqc) {

		// fundamental quote info
		FundamentalQuote fq;
		if (sqc.getFundamentalQuote() == null) {
			fq = new FundamentalQuote(sqc.getKey(), LocalDate.now());
			sqc.setFundamentalQuote(fq);
		} else {
			fq = sqc.getFundamentalQuote();
		}

		if (stats.getForwardEps() != null && stats.getForwardEps().getRaw() != null) {
			fq.setForwardEps(stats.getForwardEps().getRaw());
		}

		if (stats.getBeta() != null && stats.getBeta().getRaw() != null) {
			fq.setBeta(stats.getBeta().getRaw());
		}

		// TODO map other fundamental quote info.
	}

	private static void mapCalendarEvents(CalendarEvents calendarEvents, SecurityQuoteContainer sqc) {

		SecurityUpcomingEvents events;
		if (sqc.getUpcomingEvents() == null) {
			events = new SecurityUpcomingEvents(sqc.getKey());
			sqc.setUpcomingEvents(events);
		} else {
			events = sqc.getUpcomingEvents();
		}

		// TODO Revenue ever show up here?

		if (calendarEvents.getDividendDate() != null && calendarEvents.getDividendDate().getRaw() != null) {
			events.setNextDivDate(OspreyUtils.getLocalDateFromEpoch(calendarEvents.getDividendDate().getRaw()));
		}

		if (calendarEvents.getExDividendDate() != null && calendarEvents.getExDividendDate().getRaw() != null) {
			events.setNextExDivDate(OspreyUtils.getLocalDateFromEpoch(calendarEvents.getExDividendDate().getRaw()));
		}

		if (calendarEvents.getEarnings() != null) {

			if (calendarEvents.getEarnings().getEarningsDate().size() > 0
					&& calendarEvents.getEarnings().getEarningsDate().get(0).getRaw() != null) {
				events.setNextEarningsDateEstLow(OspreyUtils
						.getLocalDateFromEpoch(calendarEvents.getEarnings().getEarningsDate().get(0).getRaw()));

				if (calendarEvents.getEarnings().getEarningsDate().size() > 1
						&& calendarEvents.getEarnings().getEarningsDate().get(1).getRaw() != null) {
					events.setNextEarningsDateEstHigh(OspreyUtils
							.getLocalDateFromEpoch(calendarEvents.getEarnings().getEarningsDate().get(1).getRaw()));
				}

			}
		}

	}

}
