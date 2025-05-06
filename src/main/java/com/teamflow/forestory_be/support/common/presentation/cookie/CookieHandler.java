package com.teamflow.forestory_be.support.common.presentation.cookie;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieHandler {

    private static final Long DELETE_COOKIE_MAX_AGE = 0L;
    private static final String DELETE_COOKIE_VALUE = "";
    private static final String LOCAL_HOST = "localhost";

    private final CookieProperties properties;

    public ResponseCookie generateCookie(String key, String value) {
        return generateCookieWithMaxAge(key, value, properties.maxAge());
    }

    public ResponseCookie deleteCookie(String key) {
        return generateCookieWithMaxAge(key, DELETE_COOKIE_VALUE, DELETE_COOKIE_MAX_AGE);
    }

    private ResponseCookie generateCookieWithMaxAge(String key, String value, Long maxAge) {
        ResponseCookieBuilder cookieBuilder = ResponseCookie.from(key, value)
            .maxAge(maxAge)
            .path(properties.path())
            .sameSite(properties.sameSite())
            .secure(properties.secure())
            .httpOnly(properties.httpOnly());
        if (!properties.domain().equalsIgnoreCase(LOCAL_HOST)) {
            cookieBuilder.domain(properties.domain());
        }
        return cookieBuilder.build();
    }
}
