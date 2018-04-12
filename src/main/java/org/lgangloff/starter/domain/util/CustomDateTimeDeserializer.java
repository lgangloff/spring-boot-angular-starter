package org.lgangloff.starter.domain.util;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;
import org.lgangloff.starter.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Custom Jackson deserializer for transforming a JSON object to a Joda DateTime object.
 */
public class CustomDateTimeDeserializer extends JsonDeserializer<DateTime> {
    
    @Autowired
    private TimeService timeService;

    public CustomDateTimeDeserializer() {
		super();        
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);    
	}
    
    @Override
    public DateTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonToken t = jp.getCurrentToken();
        DateTimeZone dateTimeZone = timeService.getCurrentDateTimeZome();
        if (t == JsonToken.VALUE_STRING) {
            String str = jp.getText().trim();
			DateTime parseDateTime = ISODateTimeFormat.dateTimeParser().withZoneUTC().parseDateTime(str);
			DateTime dateTime = parseDateTime.withZoneRetainFields(dateTimeZone);
			return dateTime;
        }
        if (t == JsonToken.VALUE_NUMBER_INT) {
        	DateTime dateTime = new DateTime(jp.getLongValue(), dateTimeZone);
			DateTime dateTimeZoned = dateTime.withZoneRetainFields(dateTimeZone);
			return dateTimeZoned;
        }
        ctxt.handleUnexpectedToken(handledType(), jp);
        return null;
    }
}
