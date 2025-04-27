package com.teamflow.forestory_be.user.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.f4b6a3.tsid.TsidFactory;
import com.teamflow.forestory_be.support.common.util.TsidGenerator;
import com.teamflow.forestory_be.user.domain.vo.Name;
import com.teamflow.forestory_be.user.domain.vo.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @BeforeEach
    void setUp() {
        TsidGenerator.initialize(TsidFactory.newInstance256(0));
    }

    @Test
    @DisplayName("유저를 정상적으로 생성한다.")
    void should_create_user() {
        // given
        Name name = new Name("name");
        String profileImageUrl = "https://image.com/test.png";

        // when
        User user = User.create(name, profileImageUrl);

        // then
        assertThat(user.getId()).isNotNull();
        assertThat(user.getName()).isEqualTo(new Name("name"));
        assertThat(user.getStatus()).isEqualTo(UserStatus.ONBOARDING);
    }

    @Test
    @DisplayName("유저 이름을 정상적으로 수정한다.")
    void should_update_user_name() {
        // given
        User user = User.create(new Name("name"), "https://image.com/test.png");

        // when
        User updatedUser = user.update(1L, new Name("newname"), "https://image.com/test.png");

        // then
        assertThat(user.getId()).isEqualTo(updatedUser.getId());
        assertThat(updatedUser.getName()).isEqualTo(new Name("newname"));
        assertThat(updatedUser.getProfileImageUrl()).isEqualTo("https://image.com/test.png");
    }
}
