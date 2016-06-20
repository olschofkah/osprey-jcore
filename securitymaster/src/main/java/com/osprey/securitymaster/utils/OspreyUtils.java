package com.osprey.securitymaster.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.osprey.securitymaster.constants.OspreyConstants;

public class OspreyUtils {

	public static ZonedDateTime getZonedDateTimeAtStartOfDay() {
		return LocalDate.now().atStartOfDay(ZoneId.of(OspreyConstants.ZONED_DATE_TIME_ZONE_ID_NY));
	}

	public static ZonedDateTime getZonedDateTimeAtStartOfDay(ZonedDateTime zdt) {
		return zdt.toLocalDate().atStartOfDay(ZoneId.of(OspreyConstants.ZONED_DATE_TIME_ZONE_ID_NY));
	}

	public static ZonedDateTime getZonedDateTimeFromEpoch(long epoch) {
		Instant i = Instant.ofEpochSecond(epoch);
		return ZonedDateTime.ofInstant(i, ZoneId.of(OspreyConstants.ZONED_DATE_TIME_ZONE_ID_NY));
	}

	public static LocalDate getLocalDateFromEpoch(long epoch) {
		return getZonedDateTimeFromEpoch(epoch).toLocalDate();
	}

}
