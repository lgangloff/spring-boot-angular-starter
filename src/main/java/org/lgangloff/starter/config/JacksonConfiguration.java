package org.lgangloff.starter.config;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

@Configuration
public class JacksonConfiguration {

    public static class EmptyToNullStringDeserializer extends JsonDeserializer<String> {

		@Override
		public String deserialize(JsonParser p, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			return StringUtils.isBlank(p.getValueAsString()) ? null : p.getValueAsString();
		}

	}

	@Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();

        builder.deserializerByType(String.class, new EmptyToNullStringDeserializer());
        
        return builder;
    }
}