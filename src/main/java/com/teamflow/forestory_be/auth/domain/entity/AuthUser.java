package com.teamflow.forestory_be.auth.domain.entity;

import com.teamflow.forestory_be.auth.domain.vo.AuthMethod;
import com.teamflow.forestory_be.auth.domain.vo.EmailAuth;
import com.teamflow.forestory_be.auth.domain.vo.SocialAuth;
import com.teamflow.forestory_be.auth.domain.vo.SocialType;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import java.util.Objects;
import lombok.Getter;

// TODO: 비밀번호 암호화 로직 필요
@Getter
public class AuthUser {

    private final Long id;
    private final Long userId;
    private final AuthMethod authMethod;

    private AuthUser(Long id, Long userId, AuthMethod authMethod) {
        this.id = Objects.requireNonNull(id);
        this.userId = Objects.requireNonNull(userId);
        this.authMethod = Objects.requireNonNull(authMethod);
    }

    public static AuthUser createSocial(Long userId, String socialId, SocialType socialType) {
        Long id = TsidGenerator.generate();
        SocialAuth socialAuth = new SocialAuth(socialId, socialType);
        return new AuthUser(id, userId, socialAuth);
    }

    public static AuthUser createEmail(Long userId, String email, String password, String firebaseUid) {
        Long id = TsidGenerator.generate();
        EmailAuth emailAuth = new EmailAuth(email, password, firebaseUid);
        return new AuthUser(id, userId, emailAuth);
    }
}
