package com.teamflow.forestory_be.auth.infrastructure.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
    private String secretKey;
    private Long accessTokenExpiration;
    private Long refreshTokenExpiration;
}
