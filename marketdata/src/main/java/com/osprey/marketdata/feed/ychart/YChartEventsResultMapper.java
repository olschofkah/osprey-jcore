package com.osprey.marketdata.feed.ychart;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.osprey.marketdata.feed.ychart.pojo.Event;
import com.osprey.marketdata.feed.ychart.pojo.Events;
import com.osprey.marketdata.feed.ychart.pojo.YChartEventGroup;
import com.osprey.securitymaster.SecurityEvent;
import com.osprey.securitymaster.SecurityQuoteContainer;
import com.osprey.securitymaster.constants.SecurityEventType;

public class YChartEventsResultMapper {
	private final static Logger logger = LogManager.getLogger(YChartEventsResultMapper.class);
	
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm z");

	public static SecurityQuoteContainer map(Events events, SecurityQuoteContainer sqc) {

		if (events != null && events.getEvents() != null && !events.getEvents().isEmpty()) {

			ZonedDateTime now = ZonedDateTime.now();
			Set<SecurityEvent> mappedEvents = new HashSet<>();

			for (Event event : events.getEvents()) {

				SecurityEventType mappedType = null;
				
				logger.trace("Mapping Event {}", () -> event);

				switch (YChartEventGroup.fromValue(event.getEventGroup())) {
				case SPLITS:
				case SPINOFFS:
					mappedType = SecurityEventType.CORPORATE_ACTION;
					break;
				case DIVIDENDS:
					mappedType = SecurityEventType.DIV;
					break;
				case EARNINGS:
					mappedType = SecurityEventType.EARNINGS_ACT;
					break;
				case MISC:
					mappedType = SecurityEventType.MISC;
					break;
				case OTHER:
				default:
					break;

				}

				if (mappedType != null) {
					LocalTime time = null;
					try {
						time = event.getLocalBeginTime() == null ? null
								: LocalTime.parse(event.getLocalBeginTime(), TIME_FORMATTER);
					} catch (DateTimeParseException e) {
						logger.error("Failed to parse time {} for symbol {}",
								new Object[] { event.getLocalBeginTime(), sqc.getKey().getSymbol() });
					}
					
					SecurityEvent mappedEvent = new SecurityEvent(sqc.getKey(),
							LocalDate.parse(event.getLocalBeginDate(), DATE_FORMATTER),
							time,
							mappedType,
							0.0,
							event.getEventDescription(),
							now);

					mappedEvents.add(mappedEvent);
				}
			}

			sqc.setEvents(new ArrayList<>(mappedEvents));
		}

		return sqc;
	}

}
