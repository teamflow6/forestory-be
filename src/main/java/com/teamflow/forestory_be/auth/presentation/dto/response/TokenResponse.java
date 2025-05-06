package com.teamflow.forestory_be.auth.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record TokenResponse(
    String accessToken,
    @JsonIgnore String refreshToken
) {
}
