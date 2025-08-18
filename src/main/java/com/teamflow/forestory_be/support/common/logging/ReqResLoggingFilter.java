package com.teamflow.forestory_be.support.common.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class ReqResLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ReqResLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();
        filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);
        long endTime = System.currentTimeMillis();
        double elapsedTime = (endTime - startTime) / 1000.0;

        try {
            String logMessage = HttpLogMessage.create(cachingRequestWrapper, cachingResponseWrapper, elapsedTime)
                .toPrettierLog();
            log.info(logMessage);
            cachingResponseWrapper.copyBodyToResponse();
        } catch (Exception ex) {
            log.error("[{}] Failure Logging: {}", this.getClass().getSimpleName(), ex.getMessage(), ex);
        }
    }
}
