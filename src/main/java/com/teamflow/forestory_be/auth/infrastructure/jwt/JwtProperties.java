package com.teamflow.forestory_be.auth.infrastructure.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
    String secretKey,
    Long accessTokenExpiration,
    Long refreshTokenExpiration
) {
}
