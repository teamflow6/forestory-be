package com.teamflow.forestory_be.auth.infrastructure.jwt;

import com.teamflow.forestory_be.auth.domain.exception.AlreadyExpiredTokenException;
import com.teamflow.forestory_be.auth.domain.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtExtractor {

    private static final String USER_ID = "user_id";
    private static final String TOKEN_ID = "token_id";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    private final JwtParser jwtParser;

    public JwtExtractor(JwtProperties jwtProperties) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build();
    }

    public Long extractAccessToken(String token) {
        return Long.parseLong(extract(token, ACCESS_TOKEN, USER_ID, String.class));
    }

    public Map<String, Long> extractRefreshToken(String token) {
        Long userId = Long.parseLong(extract(token, REFRESH_TOKEN, USER_ID, String.class));
        Long tokenId = Long.parseLong(extract(token, REFRESH_TOKEN, TOKEN_ID, String.class));
        return Map.of("userId", userId, "tokenId", tokenId);
    }

    private <T> T extract(String token, String type, String claimKey, Class<T> T) {
        Claims claims = parseClaim(token);
        String subject = claims.getSubject();
        T claimValue = claims.get(claimKey, T);

        if (claimValue != null && subject.equals(type)) {
            return claimValue;
        }
        throw new InvalidTokenException();
    }

    private Claims parseClaim(String token) {
        try {
            return jwtParser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            throw new AlreadyExpiredTokenException(token);
        } catch (Exception ex) {
            throw new InvalidTokenException();
        }
    }
}
