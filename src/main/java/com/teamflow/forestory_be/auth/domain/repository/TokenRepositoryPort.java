package com.teamflow.forestory_be.auth.domain.repository;

import com.teamflow.forestory_be.auth.domain.entity.Token;

public interface TokenRepositoryPort {
    Token save(Token token);

    Token getByUserId(Long userId);

    void deleteByUserId(Long userId);
}
