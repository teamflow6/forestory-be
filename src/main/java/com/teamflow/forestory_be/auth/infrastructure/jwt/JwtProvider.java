package com.teamflow.forestory_be.auth.infrastructure.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private static final String CLAIM_USER_ID = "user_id";
    private static final String CLAIM_TOKEN_ID = "token_id";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    private final SecretKey secretKey;
    private final JwtProperties jwtProperties;

    public JwtProvider(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.secretKey()));
        this.jwtProperties = jwtProperties;
    }

    public String generateAccessToken(Long userId) {
        Claims claims = Jwts.claims(Map.of(CLAIM_USER_ID, userId.toString()));
        return generateToken(claims, ACCESS_TOKEN, jwtProperties.accessTokenExpiration());
    }

    public String generateRefreshToken(Long userId, Long tokenId) {
        Claims claims = Jwts.claims(Map.of(
            CLAIM_USER_ID, userId.toString(),
            CLAIM_TOKEN_ID, tokenId.toString())
        );
        return generateToken(claims, REFRESH_TOKEN, jwtProperties.refreshTokenExpiration());
    }

    private String generateToken(Claims claims, String subject, Long expire) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expire))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }
}
