package com.teamflow.forestory_be.user.domain.entity;

import com.teamflow.forestory_be.support.common.util.TsidGenerator;
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

    private User(Long id, Name name, String profileImageUrl, UserStatus status) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.profileImageUrl = profileImageUrl;
        this.status = Objects.requireNonNull(status);
    }

    public User completeOnboarding(Long userId, Name name, String profileImageUrl) {
        validateUser(userId);
        validateStatus(UserStatus.ONBOARDING);
        return new User(
            this.id,
            updateIfDifferent(name, this.name),
            updateIfDifferent(profileImageUrl, this.profileImageUrl),
            UserStatus.ACTIVE
        );
    }

    public User update(Long userId, Name name, String profileImageUrl) {
        validateUser(userId);
        validateStatus(UserStatus.ACTIVE);
        return new User(
            this.id,
            updateIfDifferent(name, this.name),
            updateIfDifferent(profileImageUrl, this.profileImageUrl),
            this.status
        );
    }

    public static User create(Name name, String profileImageUrl) {
        Long id = TsidGenerator.generate();
        return new User(id, name, profileImageUrl, UserStatus.ONBOARDING);
    }

    public static User reconstruct(Long id, Name name, String profileImageUrl, UserStatus status) {
        return new User(id, name, profileImageUrl, status);
    }

    private void validateUser(Long userId) {
        if (this.id != userId) {
            throw new IllegalArgumentException("권한이 없는 사용자입니다.");
        }
    }

    private void validateStatus(UserStatus status) {
        if (this.status != status) {
            throw new IllegalArgumentException("올바르지 않은 상태의 사용자입니다.");
        }
    }

    // TODO: 추후 Util 클래스 분리 고려
    private static <T> T updateIfDifferent(T newValue, T currentValue) {
        if (newValue == null || newValue.equals(currentValue)) {
            return currentValue;
        }
        return newValue;
    }
}
