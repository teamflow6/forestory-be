package com.teamflow.forestory_be.auth.infrastructure.security.oauth;

import com.teamflow.forestory_be.auth.application.AuthService;
import com.teamflow.forestory_be.auth.application.dto.SocialLoginCommand;
import com.teamflow.forestory_be.auth.domain.vo.SocialType;
import com.teamflow.forestory_be.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AuthService authService;
    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);

        String socialId = oAuth2User.getName();
        String registrationId = request.getClientRegistration().getRegistrationId();
        SocialType socialType = SocialType.from(registrationId);

        SocialLoginCommand command = new SocialLoginCommand(socialId, socialType);
        Long userId = authService.socialLogin(command);

        String token = request.getAccessToken().getTokenValue();
        return new CustomOAuth2User(userId, token, oAuth2User);
    }
}
