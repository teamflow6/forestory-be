package com.teamflow.forestory_be.auth.domain.vo;

import com.teamflow.forestory_be.auth.domain.exception.NotSupportSocialTypeException;
import java.util.Arrays;

public enum SocialType {
    EMAIL,
    KAKAO,
    ;

    public static SocialType from(String value) {
        return Arrays.stream(values())
            .filter(type -> type.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new NotSupportSocialTypeException(value));
    }
}
