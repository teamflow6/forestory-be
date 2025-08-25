package com.teamflow.forestory_be.user.domain.entity;

import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import com.teamflow.forestory_be.user.domain.vo.ContactUrl;
import com.teamflow.forestory_be.user.domain.vo.Introduction;
import com.teamflow.forestory_be.user.domain.vo.Name;
import com.teamflow.forestory_be.user.domain.vo.ProfileImageUrl;
import com.teamflow.forestory_be.user.domain.vo.UserStatus;
import java.util.Objects;
import lombok.Getter;

@Getter
public class User {

    private final Long id;
    private final Name name;
    private final UserStatus status;
    private final Introduction introduction;
    private final ContactUrl contactUrl;
    private final ProfileImageUrl profileImageUrl;

    private User(
        Long id,
        Name name,
        UserStatus status,
        ContactUrl contactUrl,
        Introduction introduction,
        ProfileImageUrl profileImageUrl
    ) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.profileImageUrl = profileImageUrl;
        this.status = Objects.requireNonNull(status);
        this.introduction = introduction; // <- 세팅(초기엔 null 가능)
        this.contactUrl = contactUrl;
    }

    public User completeOnboarding(Long userId, Name name, ProfileImageUrl profileImageUrl) {
        validateUser(userId);
        return new User(
            this.id,
            updateIfDifferent(name, this.name),
            UserStatus.ACTIVE,
            ContactUrl.empty(),
            Introduction.empty(),
            updateIfDifferent(profileImageUrl, this.profileImageUrl)
        );
    }

    public User update(
        Long userId,
        Name name,
        Introduction introduction,
        ProfileImageUrl profileImageUrl,
        ContactUrl contactUrl
    ) {
        validateUser(userId);
        validateStatus(UserStatus.ACTIVE);
        return new User(
            this.id,
            updateIfDifferent(name, this.name),
            this.status,
            updateIfDifferent(contactUrl, this.contactUrl),
            updateIfDifferent(introduction, this.introduction),
            updateIfDifferent(profileImageUrl, this.profileImageUrl)
        );
    }

    public boolean isSameName(Name name) {
        return this.name.equals(name);
    }

    /**
     * 소개 변경
     */
    public User updateIntroduction(Long userId, Introduction introduction) {
        validateUser(userId);
        // 필요 시 상태 검증 추가: validateStatus(UserStatus.ACTIVE);
        return new User(
            this.id,
            this.name,
            this.status,
            this.contactUrl,
            updateIfDifferent(introduction, this.introduction),
            this.profileImageUrl
        );
    }

    public static User create(Name name, ProfileImageUrl profileImageUrl) {
        Long id = TsidGenerator.generate();
        return new User(
            id,
            name,
            UserStatus.ONBOARDING,
            ContactUrl.empty(),
            Introduction.empty(),
            profileImageUrl
        );
    }

    public static User reconstruct(
        Long id, Name name,
        ProfileImageUrl profileImageUrl,
        UserStatus status,
        Introduction introduction,
        ContactUrl contactUrl
    ) {
        return new User(
            id,
            name,
            status,
            contactUrl,
            introduction,
            profileImageUrl
        );
    }

    private void validateUser(Long userId) {
        if (!Objects.equals(this.id, userId)) {
            throw new IllegalArgumentException("권한이 없는 사용자입니다.");
        }
    }

    private void validateStatus(UserStatus status) {
        if (this.status != status) {
            throw new IllegalArgumentException("올바르지 않은 상태의 사용자입니다.");
        }
    }

    private static <T> T updateIfDifferent(T newValue, T currentValue) {
        if (newValue == null || newValue.equals(currentValue)) {
            return currentValue;
        }
        return newValue;
    }
}
