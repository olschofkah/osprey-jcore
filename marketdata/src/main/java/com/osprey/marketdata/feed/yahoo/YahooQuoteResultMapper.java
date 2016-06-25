package com.osprey.marketdata.feed.yahoo;

import com.osprey.marketdata.feed.yahoo.pojo.CalendarEvents;
import com.osprey.marketdata.feed.yahoo.pojo.DefaultKeyStatistics;
import com.osprey.marketdata.feed.yahoo.pojo.Earnings_;
import com.osprey.marketdata.feed.yahoo.pojo.FinancialData;
import com.osprey.marketdata.feed.yahoo.pojo.Price;
import com.osprey.marketdata.feed.yahoo.pojo.Result;
import com.osprey.marketdata.feed.yahoo.pojo.SummaryDetail;
import com.osprey.marketdata.feed.yahoo.pojo.SummaryProfile;
import com.osprey.securitymaster.FundamentalPricedSecurity;
import com.osprey.securitymaster.PricedSecurity;
import com.osprey.securitymaster.utils.OspreyUtils;

public class YahooQuoteResultMapper {

	public static FundamentalPricedSecurity map(Result result, FundamentalPricedSecurity security) {

		if (result.getCalendarEvents() != null) {
			mapCalendarEvents(result.getCalendarEvents(), security);
		}

		if (result.getDefaultKeyStatistics() != null) {
			mapDefaultKeyStatistics(result.getDefaultKeyStatistics(), security);
		}

		if (result.getEarnings() != null) {
			mapEarnings(result.getEarnings(), security);
		}

		if (result.getFinancialData() != null) {
			mapFinancialData(result.getFinancialData(), security);
		}

		if (result.getSummaryDetail() != null) {
			mapSummaryDetail(result.getSummaryDetail(), security);
		}

		if (result.getSummaryProfile() != null) {
			mapSummaryProfile(result.getSummaryProfile(), security);
		}

		if (result.getPrice() != null) {
			mapPrice(result.getPrice(), security);
		}

		return security;
	}

	private static void mapPrice(Price p, PricedSecurity s) {

		// price.getExchange(); TODO need?
		// price.getOpenInterest();
		// price.getQuoteType();

		if (p.getRegularMarketPrice() != null && p.getRegularMarketPrice().getRaw() != null) {
			s.setLastPrice(p.getRegularMarketPrice().getRaw());
		}
	}

	private static void mapSummaryProfile(SummaryProfile summaryProfile, FundamentalPricedSecurity s) {
		// TODO Determine if summary profile data is desired.
	}

	private static void mapSummaryDetail(SummaryDetail sd, FundamentalPricedSecurity s) {

		if (sd.getAsk() != null && sd.getAsk().getRaw() != null) {
			s.setAsk(sd.getAsk().getRaw());
		}

		if (sd.getBid() != null && sd.getBid().getRaw() != null) {
			s.setBid(sd.getBid().getRaw());
		}

		if (sd.getBidSize() != null && sd.getBidSize().getRaw() != null) {
			s.setBidSize(sd.getBidSize().getRaw());
		}

		if (sd.getAskSize() != null && sd.getAskSize().getRaw() != null) {
			s.setAskSize(sd.getAskSize().getRaw());
		}

		if (sd.getAverageVolume() != null && sd.getAverageVolume().getRaw() != null) {
			s.setAverageVolume(sd.getAverageVolume().getRaw());
		}

		if (sd.getBeta() != null && sd.getBeta().getRaw() != null) {
			s.setBeta(sd.getBeta().getRaw());
		}

		if (sd.getDayHigh() != null && sd.getDayHigh().getRaw() != null) {
			s.setDayHigh(sd.getDayHigh().getRaw());
		}

		if (sd.getDayLow() != null && sd.getDayLow().getRaw() != null) {
			s.setDayLow(sd.getDayLow().getRaw());
		}

		if (sd.getDividendRate() != null && sd.getDividendRate().getRaw() != null) {
			s.setAnnualDividend(sd.getDividendRate().getRaw());
		}

		if (sd.getDividendYield() != null && sd.getDividendYield().getRaw() != null) {
			s.setAnnualYield(sd.getDividendYield().getRaw());
		}

		if (sd.getFiftyTwoWeekHigh() != null && sd.getFiftyTwoWeekHigh().getRaw() != null) {
			s.set_52High(sd.getFiftyTwoWeekHigh().getRaw());
		}

		if (sd.getFiftyTwoWeekLow() != null && sd.getFiftyTwoWeekLow().getRaw() != null) {
			s.set_52Low(sd.getFiftyTwoWeekLow().getRaw());
		}

		if (sd.getMarketCap() != null && sd.getMarketCap().getRaw() != null) {
			s.setMarketCap(sd.getMarketCap().getRaw());
		}

		if (sd.getForwardPE() != null && sd.getForwardPE().getRaw() != null) {
			// TODO not sure PE is right
			s.setPeRatio(sd.getForwardPE().getRaw());
		}

		if (sd.getOpen() != null && sd.getOpen().getRaw() != null) {
			s.setOpen(sd.getOpen().getRaw());
		}

		if (sd.getPreviousClose() != null && sd.getPreviousClose().getRaw() != null) {
			s.setPreviousClose(sd.getPreviousClose().getRaw());
		}

		if (sd.getPreviousClose() != null && sd.getPreviousClose().getRaw() != null) {
			s.setPreviousClose(sd.getPreviousClose().getRaw());
		}

		if (sd.getVolume() != null && sd.getVolume().getRaw() != null) {
			s.setVolume(sd.getVolume().getRaw());
		}

		// TODO need either of these data points?
		// sd.getPriceToSalesTrailing12Months();
		// sd.getTotalAssets();
	}

	private static void mapFinancialData(FinancialData fd, FundamentalPricedSecurity s) {

		if (fd.getDebtToEquity() != null && fd.getDebtToEquity().getRaw() != null) {
			s.setDebtToEquity(fd.getDebtToEquity().getRaw());
		}
		if (fd.getReturnOnAssets() != null && fd.getReturnOnAssets().getRaw() != null) {
			s.setReturnOnAssets(fd.getReturnOnAssets().getRaw());
		}
		if (fd.getReturnOnEquity() != null && fd.getReturnOnEquity().getRaw() != null) {
			s.setReturnOnEquity(fd.getReturnOnEquity().getRaw());
		}
		if (fd.getRevenuePerShare() != null && fd.getRevenuePerShare().getRaw() != null) {
			s.setRevenuePerShare(fd.getRevenuePerShare().getRaw());
		}
		if (fd.getRevenueGrowth() != null && fd.getRevenueGrowth().getRaw() != null) {
			s.setRevenueGrowth(fd.getRevenueGrowth().getRaw());
		}

	}

	private static void mapEarnings(Earnings_ earnings, FundamentalPricedSecurity security) {
		// TODO determine what if any stats fields we care about
	}

	private static void mapDefaultKeyStatistics(DefaultKeyStatistics stats, FundamentalPricedSecurity security) {
		// TODO determine what if any stats fields we care about
	}

	private static void mapCalendarEvents(CalendarEvents calendarEvents, FundamentalPricedSecurity security) {

		if (calendarEvents.getDividendDate() != null && calendarEvents.getDividendDate().getRaw() != null) {
			security.setNextDivDate(OspreyUtils.getLocalDateFromEpoch(calendarEvents.getDividendDate().getRaw()));
		}

		if (calendarEvents.getExDividendDate() != null && calendarEvents.getExDividendDate().getRaw() != null) {
			security.setNextExDivDate(OspreyUtils.getLocalDateFromEpoch(calendarEvents.getExDividendDate().getRaw()));
		}

		if (calendarEvents.getEarnings() != null) {

			if (calendarEvents.getEarnings().getEarningsDate().size() > 0
					&& calendarEvents.getEarnings().getEarningsDate().get(0).getRaw() != null) {
				security.setNextEarningsDateLower(OspreyUtils
						.getLocalDateFromEpoch(calendarEvents.getEarnings().getEarningsDate().get(0).getRaw()));

				if (calendarEvents.getEarnings().getEarningsDate().size() > 1
						&& calendarEvents.getEarnings().getEarningsDate().get(1).getRaw() != null) {
					security.setNextEarningsDateUpper(OspreyUtils
							.getLocalDateFromEpoch(calendarEvents.getEarnings().getEarningsDate().get(1).getRaw()));
				}

			}
		}

	}

}
