package com.teamflow.forestory_be.auth.infrastructure.redis;

import com.teamflow.forestory_be.auth.domain.entity.Token;
import com.teamflow.forestory_be.auth.domain.repository.TokenRepositoryPort;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRedisRepository implements TokenRepositoryPort {

    private static final Long TTL = 10_080L;
    private static final String USER_PREFIX = "user:";

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public Token save(Token token) {
        String key = USER_PREFIX + token.getUserId();
        String value = token.getId().toString();
        redisTemplate.opsForValue().set(key, value, TTL, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public Token getByUserId(Long userId) {
        String key = USER_PREFIX + userId;
        String value = redisTemplate.opsForValue().get(key);
        Long tokenId = Long.parseLong(value);
        return Token.of(tokenId, userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        String key = USER_PREFIX + userId;
        redisTemplate.delete(key);
    }
}
