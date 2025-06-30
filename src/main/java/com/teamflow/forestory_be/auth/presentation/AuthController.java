package com.teamflow.forestory_be.auth.presentation;

import com.teamflow.forestory_be.auth.application.AuthService;
import com.teamflow.forestory_be.auth.application.FirebaseService;
import com.teamflow.forestory_be.auth.application.dto.DeleteTokenCommand;
import com.teamflow.forestory_be.auth.application.dto.FirebaseLoginCommand;
import com.teamflow.forestory_be.auth.application.dto.IssueTokenCommand;
import com.teamflow.forestory_be.auth.application.dto.ReissueTokenCommand;
import com.teamflow.forestory_be.auth.infrastructure.jwt.JwtExtractor;
import com.teamflow.forestory_be.auth.infrastructure.jwt.JwtProvider;
import com.teamflow.forestory_be.auth.presentation.dto.response.TokenResponse;
import com.teamflow.forestory_be.support.common.presentation.cookie.CookieHandler;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private static final String USER_ID = "userId";
    private static final String TOKEN_ID = "tokenId";
    private static final String REFRESH_TOKEN = "refresh_token";

    private final AuthService authService;
    private final FirebaseService firebaseService;
    private final CookieHandler cookieHandler;
    private final JwtExtractor jwtExtractor;
    private final JwtProvider jwtProvider;

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(
        @CookieValue(REFRESH_TOKEN) String refreshToken,
        HttpServletResponse response
    ) {
        Map<String, Long> tokenPair = jwtExtractor.extractRefreshToken(refreshToken);
        Long userId = tokenPair.get(USER_ID);
        Long tokenId = tokenPair.get(TOKEN_ID);

        ReissueTokenCommand command = new ReissueTokenCommand(userId, tokenId);
        Long rotatedTokenId = authService.reissueToken(command);

        return createTokenResponse(userId, rotatedTokenId, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @AuthenticationPrincipal Long userId,
        HttpServletResponse response
    ) {
        DeleteTokenCommand command = new DeleteTokenCommand(userId);
        authService.deleteToken(command);

        ResponseCookie expiredCookie = cookieHandler.deleteCookie(REFRESH_TOKEN);
        response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/firebase/login")
    public ResponseEntity<TokenResponse> firebaseLogin(
        @RequestHeader("Authorization") String idToken,
        HttpServletResponse response
    ) {
        String token = idToken.substring(7);
        FirebaseLoginCommand command = firebaseService.verify(token);

        Long userId = authService.firebaseLogin(command);
        Long tokenId = authService.issueToken(new IssueTokenCommand(userId));
        return createTokenResponse(userId, tokenId, response);
    }

    private ResponseEntity<TokenResponse> createTokenResponse(Long userId, Long tokenId, HttpServletResponse response) {
        String accessToken = jwtProvider.generateAccessToken(userId);
        String refreshToken = jwtProvider.generateRefreshToken(userId, tokenId);

        ResponseCookie cookie = cookieHandler.generateCookie(REFRESH_TOKEN, refreshToken);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken));
    }
}
