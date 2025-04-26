package com.teamflow.forestory_be.auth.application.dto;

import com.teamflow.forestory_be.auth.domain.vo.SocialType;

public record SocialLoginCommand(
    String socialId,
    SocialType socialType
) {
}
