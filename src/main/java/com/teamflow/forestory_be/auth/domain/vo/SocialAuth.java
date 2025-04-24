package com.teamflow.forestory_be.auth.domain.vo;

import java.util.Objects;

public record SocialAuth(String socialId, SocialType socialType) implements AuthMethod {

    public SocialAuth {
        Objects.requireNonNull(socialId, "socialId must not be null");
        Objects.requireNonNull(socialType, "socialType must not be null");
    }
}

