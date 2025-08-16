package com.teamflow.forestory_be.user.domain.entity;

import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import com.teamflow.forestory_be.user.domain.vo.Introduction;
import com.teamflow.forestory_be.user.domain.vo.Name;
import com.teamflow.forestory_be.user.domain.vo.UserStatus;
import java.util.Objects;
import lombok.Getter;

@Getter
public class User {

    private final Long id;
    private final Name name;
    private final String profileImageUrl;
    private final UserStatus status;
    private final Introduction introduction;

    private User(Long id, Name name, String profileImageUrl, UserStatus status, Introduction introduction) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.profileImageUrl = profileImageUrl;
        this.status = Objects.requireNonNull(status);
        this.introduction = introduction; // <- 세팅(초기엔 null 가능)
    }

    public User completeOnboarding(Long userId, Name name, Introduction introduction, String profileImageUrl) {
        validateUser(userId);
        return new User(
                this.id,
                updateIfDifferent(name, this.name),
                updateIfDifferent(profileImageUrl, this.profileImageUrl),
                UserStatus.ACTIVE,
                updateIfDifferent(introduction, this.introduction)
        );
    }

    public User update(Long userId, Name name, String profileImageUrl) {
        validateUser(userId);
        validateStatus(UserStatus.ACTIVE);
        return new User(
                this.id,
                updateIfDifferent(name, this.name),
                updateIfDifferent(profileImageUrl, this.profileImageUrl),
                this.status,
                this.introduction
        );
    }

    /** 소개 변경 */
    public User updateIntroduction(Long userId, Introduction introduction) {
        validateUser(userId);
        // 필요 시 상태 검증 추가: validateStatus(UserStatus.ACTIVE);
        return new User(
                this.id,
                this.name,
                this.profileImageUrl,
                this.status,
                updateIfDifferent(introduction, this.introduction)
        );
    }

    public static User create(Name name, String profileImageUrl) {
        Long id = TsidGenerator.generate();
        return new User(id, name, profileImageUrl, UserStatus.ONBOARDING, new Introduction(""));
    }

    public static User reconstruct(Long id, Name name, String profileImageUrl, UserStatus status, Introduction introduction) {
        return new User(id, name, profileImageUrl, status, introduction != null ? introduction : new Introduction(""));
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
        if (newValue == null || newValue.equals(currentValue)) return currentValue;
        return newValue;
    }
}
