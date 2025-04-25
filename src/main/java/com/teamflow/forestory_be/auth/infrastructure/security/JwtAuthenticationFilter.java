package com.teamflow.forestory_be.auth.infrastructure.security;

import com.teamflow.forestory_be.auth.domain.exception.InvalidTokenException;
import com.teamflow.forestory_be.auth.infrastructure.jwt.JwtExtractor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtExtractor jwtExtractor;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String accessToken = extractAccessToken(request);

        if (accessToken != null) {
            processAuthentication(accessToken);
        }
        filterChain.doFilter(request, response);
    }

    private String extractAccessToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    private void processAuthentication(String accessToken) {
        try {
            Long userId = jwtExtractor.extractAccessToken(accessToken);
            UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userId, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            throw new InvalidTokenException();
        }
    }
}
