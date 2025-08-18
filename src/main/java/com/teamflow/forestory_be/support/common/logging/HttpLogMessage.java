package com.teamflow.forestory_be.support.common.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HttpLogMessage {

    private final String httpMethod;
    private final String requestUri;
    private final String clientIp;
    private final double elapsedTime;
    private final String requestHeaders;
    private final String responseHeaders;
    private final String requestParam;
    private final String requestBody;
    private final String responseBody;

    private HttpLogMessage(
        String httpMethod,
        String requestUri, HttpStatus httpStatus,
        String clientIp,
        double elapsedTime,
        String requestHeaders,
        String responseHeaders,
        String requestParam,
        String requestBody,
        String responseBody
    ) {
        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.clientIp = clientIp;
        this.elapsedTime = elapsedTime;
        this.requestHeaders = requestHeaders;
        this.responseHeaders = responseHeaders;
        this.requestParam = requestParam;
        this.requestBody = requestBody;
        this.responseBody = responseBody;
    }

    public static HttpLogMessage create(ContentCachingRequestWrapper requestWrapper,
        ContentCachingResponseWrapper responseWrapper,
        double elapsedTime) {

        return new HttpLogMessage(
            requestWrapper.getMethod(),
            requestWrapper.getRequestURI(),
            HttpStatus.valueOf(responseWrapper.getStatus()),
            requestWrapper.getRemoteAddr(),
            elapsedTime,
            getRequestHeaders(requestWrapper),
            getResponseHeaders(responseWrapper),
            getRequestParams(requestWrapper),
            new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8),
            new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8)
        );
    }

    private static String getRequestHeaders(ContentCachingRequestWrapper request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder sb = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            sb.append(headerName).append(": ").append(request.getHeader(headerName)).append(", ");
        }
        return sb.toString().replaceAll(", $", "");
    }

    private static String getResponseHeaders(ContentCachingResponseWrapper response) {
        Set<String> headerNames = new HashSet<>(response.getHeaderNames());
        return headerNames.stream()
            .map(name -> name + ": " + String.join(", ", response.getHeaders(name)))
            .collect(Collectors.joining(", "));
    }

    private static String getRequestParams(ContentCachingRequestWrapper request) {
        try {
            Map<String, String[]> params = request.getParameterMap();
            Map<String, String> flatParams = params.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> String.join(", ", e.getValue())
                ));
            return new ObjectMapper().writeValueAsString(flatParams);
        } catch (Exception e) {
            return "{}";
        }
    }

    public String toPrettierLog() {
        return String.format(
            "\n[REQUEST] %s %s (%.3fs)\n" +
                ">> CLIENT_IP: %s\n" +
                ">> REQUEST_HEADERS: %s\n" +
                ">> RESPONSE_HEADERS: %s\n" +
                ">> REQUEST_PARAM: %s\n" +
                ">> REQUEST_BODY: %s\n" +
                ">> RESPONSE_BODY: %s",
            httpMethod, requestUri, elapsedTime,
            clientIp,
            requestHeaders,
            responseHeaders,
            requestParam,
            truncateBody(requestBody),
            truncateBody(responseBody)
        );
    }

    private String truncateBody(String body) {
        return truncateBody(body, 500);
    }

    private String truncateBody(String body, int maxLength) {
        if (body == null) {
            return "";
        }
        if (body.length() <= maxLength) {
            return body;
        }
        return body.substring(0, maxLength) + "...";
    }
}
