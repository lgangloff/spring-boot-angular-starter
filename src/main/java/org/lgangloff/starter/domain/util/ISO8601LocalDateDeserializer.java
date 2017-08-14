package org.lgangloff.starter.domain.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

 /**
  * Custom Jackson deserializer for transforming a JSON object (using the ISO 8601 date format)
  * to a Joda LocalDate object.
  */
public class ISO8601LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonToken t = jp.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            String str = jp.getText().trim();
            if (StringUtils.endsWith(str, "T00:00:00.000Z")){
            	return ISODateTimeFormat.localDateParser().parseLocalDate(StringUtils.removeEnd(str, "T00:00:00.000Z"));
            }
            else{
                return ISODateTimeFormat.dateTimeParser().parseDateTime(str).toLocalDate();
            }
        }
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return new LocalDate(jp.getLongValue());
        }
        throw ctxt.mappingException(handledType());
    }
}
