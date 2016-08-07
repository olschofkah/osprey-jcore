package com.osprey.marketdata.feed.yahoo;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.marketdata.feed.yahoo.pojo.CalendarEvents;
import com.osprey.marketdata.feed.yahoo.pojo.DefaultKeyStatistics;
import com.osprey.marketdata.feed.yahoo.pojo.Earnings;
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

public class YahooQuoteResultMapper {

	final static Logger logger = LogManager.getLogger(YahooQuoteResultMapper.class);

	private static final int MAX_LONG_DESC_SIZE = 2043;

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

				// trim the size of the long business description to avoid
				// excessive sizes.
				String desc = sp.getLongBusinessSummary().trim();
				if (desc.length() > MAX_LONG_DESC_SIZE) {
					desc = desc.substring(0, MAX_LONG_DESC_SIZE) + " ...";
				}

				s.setCompanyDescription(desc);
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

		if (sd.getAverageVolume10days() != null && sd.getAverageVolume10days().getRaw() != null) {
			fq.set_10DayAvgVolume(sd.getAverageVolume10days().getRaw());
		}

		if (sd.getDividendRate() != null && sd.getDividendRate().getRaw() != null) {
			fq.setDividendRate(sd.getDividendRate().getRaw());
		}

		if (sd.getDividendYield() != null && sd.getDividendYield().getRaw() != null) {
			fq.setDividendYield(sd.getDividendYield().getRaw());
		}

		SecurityUpcomingEvents events;
		if (sqc.getUpcomingEvents() == null) {
			events = new SecurityUpcomingEvents(sqc.getKey());
			sqc.setUpcomingEvents(events);
		} else {
			events = sqc.getUpcomingEvents();
		}

		if (sd.getExDividendDate() != null && sd.getExDividendDate().getFmt() != null) {
			events.setNextExDivDate(LocalDate.parse(sd.getExDividendDate().getFmt(), DateTimeFormatter.ISO_LOCAL_DATE));
		}

		if (sd.getFiftyDayAverage() != null && sd.getFiftyDayAverage().getRaw() != null) {
			fq.set_50DayAverage(sd.getFiftyDayAverage().getRaw());
		}

		if (sd.getTwoHundredDayAverage() != null && sd.getTwoHundredDayAverage().getRaw() != null) {
			fq.set_200DayAverage(sd.getTwoHundredDayAverage().getRaw());
		}

		if (sd.getForwardPE() != null && sd.getForwardPE().getRaw() != null) {
			fq.setForwardPe(sd.getForwardPE().getRaw());
		}
		if (sd.getMarketCap() != null && sd.getMarketCap().getRaw() != null) {
			fq.setMarketCap(sd.getMarketCap().getRaw());
		}

		if (sd.getPriceToSalesTrailing12Months() != null && sd.getPriceToSalesTrailing12Months().getRaw() != null) {
			fq.setPriceToSales(sd.getPriceToSalesTrailing12Months().getRaw());
		}

		if (sd.getTotalAssets() != null && sd.getTotalAssets().getRaw() != null) {
			fq.setTotalAssets(sd.getTotalAssets().getRaw());
		}

		if (sd.getTrailingPE() != null && sd.getTrailingPE().getRaw() != null) {
			fq.setTrailingPe(sd.getTrailingPE().getRaw());
		}

		if (sd.getYield() != null && sd.getYield().getRaw() != null) {
			fq.setYield(sd.getYield().getRaw());
		}

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

		if (fd.getQuickRatio() != null && fd.getQuickRatio().getRaw() != null) {
			fq.setQuickRatio(fd.getQuickRatio().getRaw());
		}

		if (fd.getCurrentRatio() != null && fd.getCurrentRatio().getRaw() != null) {
			fq.setCurrentRatio(fd.getCurrentRatio().getRaw());
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

		if (fd.getEarningsGrowth() != null && fd.getEarningsGrowth().getRaw() != null) {
			fq.setEarningsGrowth(fd.getEarningsGrowth().getRaw());
		}

		if (fd.getEbitda() != null && fd.getEbitda().getRaw() != null) {
			fq.setEbitda(fd.getEbitda().getRaw());
		}

		if (fd.getEbitdaMargins() != null && fd.getEbitdaMargins().getRaw() != null) {
			fq.setEbitdaMargins(fd.getEbitdaMargins().getRaw());
		}

		if (fd.getFreeCashflow() != null && fd.getFreeCashflow().getRaw() != null) {
			fq.setFreeCashflow(fd.getFreeCashflow().getRaw());
		}

		if (fd.getGrossMargins() != null && fd.getGrossMargins().getRaw() != null) {
			fq.setGrossMargins(fd.getGrossMargins().getRaw());
		}

		if (fd.getGrossProfits() != null && fd.getGrossProfits().getRaw() != null) {
			fq.setGrossProfits(fd.getGrossProfits().getRaw());
		}

		if (fd.getOperatingCashflow() != null && fd.getOperatingCashflow().getRaw() != null) {
			fq.setOperatingCashflow(fd.getOperatingCashflow().getRaw());
		}

		if (fd.getOperatingMargins() != null && fd.getOperatingMargins().getRaw() != null) {
			fq.setOperatingMargins(fd.getOperatingMargins().getRaw());
		}

		if (fd.getProfitMargins() != null && fd.getProfitMargins().getRaw() != null) {
			fq.setProfitMargins(fd.getProfitMargins().getRaw());
		}

		if (fd.getTotalCash() != null && fd.getTotalCash().getRaw() != null) {
			fq.setTotalCash(fd.getTotalCash().getRaw());
		}

		if (fd.getTotalCashPerShare() != null && fd.getTotalCashPerShare().getRaw() != null) {
			fq.setTotalCashPerShare(fd.getTotalCashPerShare().getRaw());
		}

		if (fd.getTotalDebt() != null && fd.getTotalDebt().getRaw() != null) {
			fq.setTotalDebt(fd.getTotalDebt().getRaw());
		}

		if (fd.getTotalRevenue() != null && fd.getTotalRevenue().getRaw() != null) {
			fq.setTotalRevenue(fd.getTotalRevenue().getRaw());
		}

	}

	private static void mapEarnings(Earnings_ earnings, SecurityQuoteContainer sqc) {

		Set<SecurityEvent> events = new HashSet<>();

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

		if (sqc.getEvents() != null) {
			sqc.getEvents().clear();
		}
		
		List<SecurityEvent> newEvents = new ArrayList<>(events);
		sqc.setEvents(newEvents);
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

		if (stats.getEarningsQuarterlyGrowth() != null && stats.getEarningsQuarterlyGrowth().getRaw() != null) {
			fq.setEarningsQtrGrowth(stats.getEarningsQuarterlyGrowth().getRaw());
		}

		if (stats.getEarningsQuarterlyGrowth() != null && stats.getEarningsQuarterlyGrowth().getRaw() != null) {
			fq.setEarningsQtrGrowth(stats.getEarningsQuarterlyGrowth().getRaw());
		}

		if (stats.getSharesShortPriorMonth() != null && stats.getSharesShortPriorMonth().getRaw() != null) {
			fq.setSharesShortPriorMonth(stats.getSharesShortPriorMonth().getRaw());
		}

		if (stats.getForwardEps() != null && stats.getForwardEps().getRaw() != null) {
			fq.setForwardEps(stats.getForwardEps().getRaw());
		}

		if (stats.getTrailingEps() != null && stats.getTrailingEps().getRaw() != null) {
			fq.setTrailingEps(stats.getTrailingEps().getRaw());
		}

		if (stats.getBeta() != null && stats.getBeta().getRaw() != null) {
			fq.setBeta(stats.getBeta().getRaw());
		}

		if (stats.getFloatShares() != null && stats.getFloatShares().getRaw() != null) {
			fq.setFloatShares(stats.getFloatShares().getRaw());
		}

		if (stats.getEnterpriseToEbitda() != null && stats.getEnterpriseToEbitda().getRaw() != null) {
			fq.setEnterpriseToEbitda(stats.getEnterpriseToEbitda().getRaw());
		}

		if (stats.getEnterpriseToRevenue() != null && stats.getEnterpriseToRevenue().getRaw() != null) {
			fq.setEnterpriseToRevenue(stats.getEnterpriseToRevenue().getRaw());
		}

		if (stats.getEnterpriseValue() != null && stats.getEnterpriseValue().getRaw() != null) {
			fq.setEnterpriseValue(stats.getEnterpriseValue().getRaw());
		}

		if (stats.getBookValue() != null && stats.getBookValue().getRaw() != null) {
			fq.setBookValue(stats.getBookValue().getRaw());
		}

		if (stats.getPriceToBook() != null && stats.getPriceToBook().getRaw() != null) {
			fq.setPriceToBook(stats.getPriceToBook().getRaw());
		}

		if (stats.getHeldPercentInsiders() != null && stats.getHeldPercentInsiders().getRaw() != null) {
			fq.setHeldPctInsiders(stats.getHeldPercentInsiders().getRaw());
		}

		if (stats.getHeldPercentInstitutions() != null && stats.getHeldPercentInstitutions().getRaw() != null) {
			fq.setHeldPctInstitutions(stats.getHeldPercentInstitutions().getRaw());
		}

		if (stats.getNetIncomeToCommon() != null && stats.getNetIncomeToCommon().getRaw() != null) {
			fq.setNetIncomeToCommon(stats.getNetIncomeToCommon().getRaw());
		}

		if (stats.getPegRatio() != null && stats.getPegRatio().getRaw() != null) {
			fq.setPegRatio(stats.getPegRatio().getRaw());
		}

		if (stats.getPriceToBook() != null && stats.getPriceToBook().getRaw() != null) {
			fq.setPriceToBook(stats.getPriceToBook().getRaw());
		}

		if (stats.getPriceToSalesTrailing12Months() != null
				&& stats.getPriceToSalesTrailing12Months().getRaw() != null) {
			fq.setPriceToSales(stats.getPriceToSalesTrailing12Months().getRaw());
		}

		if (stats.getProfitMargins() != null && stats.getProfitMargins().getRaw() != null) {
			fq.setProfitMargins(stats.getProfitMargins().getRaw());
		}

		if (stats.getRevenueQuarterlyGrowth() != null && stats.getRevenueQuarterlyGrowth().getRaw() != null) {
			fq.setRevenueQtrGrowth(stats.getRevenueQuarterlyGrowth().getRaw());
		}

		if (stats.getSharesOutstanding() != null && stats.getSharesOutstanding().getRaw() != null) {
			fq.setSharesOutstanding(stats.getSharesOutstanding().getRaw());
		}

		if (stats.getSharesShort() != null && stats.getSharesShort().getRaw() != null) {
			fq.setSharesShort(stats.getSharesShort().getRaw());
		}

		if (stats.getShortPercentOfFloat() != null && stats.getShortPercentOfFloat().getRaw() != null) {
			fq.setShortPercentOfFloat(stats.getShortPercentOfFloat().getRaw());
		}

		if (stats.getShortRatio() != null && stats.getShortRatio().getRaw() != null) {
			fq.setShortRatio(stats.getShortRatio().getRaw());
		}

		if (stats.getTotalAssets() != null && stats.getTotalAssets().getRaw() != null) {
			fq.setTotalAssets(stats.getTotalAssets().getRaw());
		}

	}

	private static void mapCalendarEvents(CalendarEvents calendarEvents, SecurityQuoteContainer sqc) {

		SecurityUpcomingEvents events;
		if (sqc.getUpcomingEvents() == null) {
			events = new SecurityUpcomingEvents(sqc.getKey());
			sqc.setUpcomingEvents(events);
		} else {
			events = sqc.getUpcomingEvents();
		}

		if (calendarEvents.getDividendDate() != null && calendarEvents.getDividendDate().getFmt() != null) {
			events.setNextDivDate(
					LocalDate.parse(calendarEvents.getDividendDate().getFmt(), DateTimeFormatter.ISO_LOCAL_DATE));
		}

		if (calendarEvents.getExDividendDate() != null && calendarEvents.getExDividendDate().getFmt() != null) {
			events.setNextExDivDate(
					LocalDate.parse(calendarEvents.getExDividendDate().getFmt(), DateTimeFormatter.ISO_LOCAL_DATE));
		}

		if (calendarEvents.getEarnings() != null) {

			if (calendarEvents.getEarnings().getEarningsDate().size() > 0
					&& calendarEvents.getEarnings().getEarningsDate().get(0).getFmt() != null) {
				events.setNextEarningsDateEstLow(
						LocalDate.parse(calendarEvents.getEarnings().getEarningsDate().get(0).getFmt(),
								DateTimeFormatter.ISO_LOCAL_DATE));

				if (calendarEvents.getEarnings().getEarningsDate().size() > 1
						&& calendarEvents.getEarnings().getEarningsDate().get(1).getFmt() != null) {
					events.setNextEarningsDateEstHigh(
							LocalDate.parse(calendarEvents.getEarnings().getEarningsDate().get(1).getFmt(),
									DateTimeFormatter.ISO_LOCAL_DATE));
				}

			}
		}

		FundamentalQuote fq;
		if (sqc.getFundamentalQuote() == null) {
			fq = new FundamentalQuote(sqc.getKey(), LocalDate.now());
			sqc.setFundamentalQuote(fq);
		} else {
			fq = sqc.getFundamentalQuote();
		}

		Earnings earnings = calendarEvents.getEarnings();

		if (earnings.getEarningsAverage() != null && earnings.getEarningsAverage().getRaw() != null) {
			fq.setEarningsAvg(earnings.getEarningsAverage().getRaw());
		}

		if (earnings.getEarningsHigh() != null && earnings.getEarningsHigh().getRaw() != null) {
			fq.setEarningsHigh(earnings.getEarningsHigh().getRaw());
		}

		if (earnings.getEarningsLow() != null && earnings.getEarningsLow().getRaw() != null) {
			fq.setEarningsLow(earnings.getEarningsLow().getRaw());
		}

		if (earnings.getRevenueAverage() != null && earnings.getRevenueAverage().getRaw() != null) {
			fq.setRevenueAvg(earnings.getRevenueAverage().getRaw());
		}

		if (earnings.getRevenueHigh() != null && earnings.getRevenueHigh().getRaw() != null) {
			fq.setRevenueHigh(earnings.getRevenueHigh().getRaw());
		}

		if (earnings.getRevenueLow() != null && earnings.getRevenueLow().getRaw() != null) {
			fq.setRevenueLow(earnings.getRevenueLow().getRaw());
		}
	}

}
