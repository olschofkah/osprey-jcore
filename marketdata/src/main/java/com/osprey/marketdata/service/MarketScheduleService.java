package com.osprey.marketdata.service;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.osprey.securitymaster.constants.OspreyConstants;

public class MarketScheduleService {

	// TODO these methods do not work for market holidays yet ... may need a web
	// service for it.

	private static final ZoneId ZONE_ID = ZoneId.of(OspreyConstants.ZONED_DATE_TIME_ZONE_ID_NY);

	public boolean isUsEquityMarketsOpen() {
		ZonedDateTime now = ZonedDateTime.now(ZONE_ID);
		ZonedDateTime _930AmEst = now.withHour(9).withMinute(30);
		ZonedDateTime _400PmEst = now.withHour(16);

		return now.getDayOfWeek() != DayOfWeek.SATURDAY && now.getDayOfWeek() != DayOfWeek.SUNDAY
				&& now.isAfter(_930AmEst) && now.isBefore(_400PmEst);
	}

	public boolean hasUsEquityMarketsOpenedToday() {
		ZonedDateTime now = ZonedDateTime.now(ZONE_ID);
		ZonedDateTime _930AmEst = now.withHour(9).withMinute(30);
		
		return now.getDayOfWeek() != DayOfWeek.SATURDAY && now.getDayOfWeek() != DayOfWeek.SUNDAY
				&& now.isAfter(_930AmEst);
	}

}
