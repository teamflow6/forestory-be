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

    public User update(Name name, String profileImageUrl) {
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

    // TODO: 추후 Util 클래스 분리 고려
    private static <T> T updateIfDifferent(T newValue, T currentValue) {
        if (newValue == null || newValue.equals(currentValue)) {
            return currentValue;
        }
        return newValue;
    }
}
