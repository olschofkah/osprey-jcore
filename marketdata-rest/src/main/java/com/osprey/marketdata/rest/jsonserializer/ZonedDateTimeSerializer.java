package com.osprey.marketdata.rest.jsonserializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
 
@Component
public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

	public void serialize(ZonedDateTime dt, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeNumberField("epocmills", dt.toInstant().toEpochMilli());
		jgen.writeStringField("zoneddatetime", dt.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
		jgen.writeEndObject();
	}
}
