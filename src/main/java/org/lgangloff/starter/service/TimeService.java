package org.lgangloff.starter.service;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TimeService {
	
	private final Logger log = LoggerFactory.getLogger(TimeService.class);

    private String defaultTimeZoneID = "fr";

	public List<String> getAvailableTimeZones(){
    	return Arrays.asList(TimeZone.getAvailableIDs());
    }
    
    public TimeZone getCurrentTimeZome(){
    	TimeZone res = defaultTimeZoneID != null ? TimeZone.getTimeZone(defaultTimeZoneID) : DateTimeZone.UTC.toTimeZone();
    	log.trace("Using TimeZone " + res.getDisplayName());
    	return res;
    }
    
	public DateTimeZone getCurrentDateTimeZome() {
		return DateTimeZone.forTimeZone(getCurrentTimeZome());
	}

    public LocalDate nowAsLocalDate(){
    	return LocalDate.now(getCurrentDateTimeZome());
    }

    public DateTime nowAsDateTime(){
    	return DateTime.now(getCurrentDateTimeZome());
    }

	public DateTime parseDate(String pattern, String value) {
		try {
			return DateTimeFormat.forPattern(pattern).withZone(getCurrentDateTimeZome()).parseDateTime(value);
		} catch (Exception e) {
			return null;
		}
	}
}
