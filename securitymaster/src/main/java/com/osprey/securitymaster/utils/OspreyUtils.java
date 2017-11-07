package com.osprey.securitymaster.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.apache.commons.io.IOUtils;

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

	public static List<String> readLinesFromUrl(String url, int connectionTimeout, int readTimeout) throws IOException {
		InputStream in = null;
		try {
			URLConnection connection = new URL(url).openConnection();
			connection.setConnectTimeout(connectionTimeout);
			connection.setReadTimeout(readTimeout);
			in = connection.getInputStream();

			return IOUtils.readLines(in, StandardCharsets.UTF_8);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
}
