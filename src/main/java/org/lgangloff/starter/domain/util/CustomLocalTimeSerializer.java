package org.lgangloff.starter.domain.util;

import java.io.IOException;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Custom Jackson serializer for transforming a Joda DateTime object to JSON.
 */
public class CustomLocalTimeSerializer extends JsonSerializer<LocalTime> {

    private static DateTimeFormatter formatter = DateTimeFormat
            .forPattern("HH:mm:ss");

    @Override
    public void serialize(LocalTime value, JsonGenerator generator,
                          SerializerProvider serializerProvider)
            throws IOException {
        generator.writeString(formatter.print(value));
    }

}
