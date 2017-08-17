package org.lgangloff.starter.web.rest.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpHeaders;

/**
 * Utility class for http header creation.
 *
 */
public class HeaderUtil {
 
    public static HttpHeaders createAlert(String message, String... param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-app-alert", message);
        headers.add("X-app-params", Stream.of(param).collect(Collectors.joining(",", "[", "]")));
        return headers;
    }
    
    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("app." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("app." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("app." + entityName + ".deleted", param);
    }

}