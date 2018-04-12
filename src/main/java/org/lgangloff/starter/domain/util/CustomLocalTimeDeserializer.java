package org.lgangloff.starter.domain.util;

import java.io.IOException;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Custom Jackson deserializer for transforming a JSON object to a Joda DateTime object.
 */
public class CustomLocalTimeDeserializer extends JsonDeserializer<LocalTime> {

    private static DateTimeFormatter formatter = DateTimeFormat
            .forPattern("HH:mm:ss");
    
    @Override
    public LocalTime deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            String str = jp.getText().trim();
            return formatter.parseLocalTime(str);
        }
        ctxt.handleUnexpectedToken(handledType(), jp);
        return null;
    }
}
