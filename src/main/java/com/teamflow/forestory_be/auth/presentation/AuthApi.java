package com.teamflow.forestory_be.auth.presentation;

import com.teamflow.forestory_be.auth.presentation.dto.response.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "인증 API", description = "인증 관련 API")
@RequestMapping("/api/v1/auth")
public interface AuthApi {

    String REFRESH_TOKEN = "refresh_token";

    @Operation(
        summary = "Access Token 재발급",
        description = "쿠키에 저장된 Refresh Token을 사용해 Access Token을 재발급합니다."
    )
    @PostMapping("/reissue")
    ResponseEntity<TokenResponse> reissue(
        @Parameter(description = "Refresh Token", hidden = true)
        @CookieValue(REFRESH_TOKEN) String refreshToken,

        @Parameter(hidden = true)
        HttpServletResponse response
    );

    @Operation(
        summary = "로그아웃",
        description = "Refresh Token을 만료시켜 로그아웃합니다.",
        security = @SecurityRequirement(name = "AccessToken")
    )
    @PostMapping("/logout")
    ResponseEntity<Void> logout(
        @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
        @Parameter(hidden = true) HttpServletResponse response
    );

    @Operation(
        summary = "Firebase 로그인",
        description = "클라이언트에서 발급받은 Firebase ID Token을 Authorization 헤더에 담아 전달합니다.",
        security = @SecurityRequirement(name = "IdToken")
    )
    @PostMapping("/firebase/login")
    ResponseEntity<TokenResponse> firebaseLogin(
        @Parameter(
            description = "Firebase ID Token",
            required = true,
            example = "Bearer eyJhbGciOiJIUzI1..."
        )
        @RequestHeader("Authorization") String idToken,

        @Parameter(hidden = true) HttpServletResponse response
    );
}
