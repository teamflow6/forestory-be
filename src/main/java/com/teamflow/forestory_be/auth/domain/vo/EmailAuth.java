package com.teamflow.forestory_be.auth.domain.vo;

import java.util.Objects;

public record EmailAuth(
    String email,
    String password,
    String firebaseUid
) implements AuthMethod {

    public EmailAuth {
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(password, "password must not be null");
    }

    public SocialType socialType() {
        return SocialType.EMAIL;
    }
}

