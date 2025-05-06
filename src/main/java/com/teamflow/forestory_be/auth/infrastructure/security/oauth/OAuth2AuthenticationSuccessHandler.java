package com.teamflow.forestory_be.auth.infrastructure.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamflow.forestory_be.auth.application.AuthService;
import com.teamflow.forestory_be.auth.application.dto.IssueTokenCommand;
import com.teamflow.forestory_be.auth.infrastructure.jwt.JwtProvider;
import com.teamflow.forestory_be.support.common.presentation.cookie.CookieHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final CookieHandler cookieHandler;
    private final ObjectMapper objectMapper;

    @Value("${app.base-uri}")
    private String baseUri;

    // TODO: 추후 인증 성공시 클라이언트로 리다이렉트 하도록 변경
    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Long userId = oAuth2User.userId();

        IssueTokenCommand command = new IssueTokenCommand(userId);
        Long tokenId = authService.issueToken(command);

        String accessToken = jwtProvider.generateAccessToken(userId);
        String refreshToken = jwtProvider.generateRefreshToken(userId, tokenId);

        ResponseCookie cookie = cookieHandler.generateCookie(REFRESH_TOKEN, refreshToken);

        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        Map<String, String> responseBody = Map.of(ACCESS_TOKEN, accessToken);
        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
