package com.teamflow.forestory_be.auth.domain.entity;

import com.teamflow.forestory_be.auth.domain.exception.UnMatchUserException;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import lombok.Getter;

@Getter
public class Token {

    private final Long id;
    private final Long userId;

    private Token(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }

    public void validateUserId(Long userId) {
        if (!this.userId.equals(userId)) {
            throw new UnMatchUserException(userId.toString());
        }
    }

    public static Token create(Long userId) {
        Long id = TsidGenerator.generate();
        return new Token(id, userId);
    }

    public static Token of(Long tokenId, Long userId) {
        return new Token(tokenId, userId);
    }
}
