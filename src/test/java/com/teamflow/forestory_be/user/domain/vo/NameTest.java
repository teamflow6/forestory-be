package com.teamflow.forestory_be.user.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.teamflow.forestory_be.user.domain.exception.InvalidUserNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NameTest {

    @Nested
    @DisplayName("정상 케이스")
    class ValidNameTest {

        @Test
        @DisplayName("정상적인 이름을 생성한다.")
        void should_create_valid_name() {
            //  given & when
            Name name = new Name("test");

            // then
            assertThat(name).isEqualTo(new Name("test"));
        }

        @Test
        @DisplayName("이름이 최소/최대 길이일 때 생성된다.")
        void should_create_name_when_length_is_min_or_max() {
            // given & when & then
            assertThatCode(() -> new Name("ab"))
                .doesNotThrowAnyException();
            assertThatCode(() -> new Name("abcdefg"))
                .doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("실패 케이스")
    class InvalidNameTest {

        @Test
        @DisplayName("이름이 null이면 예외가 발생한다.")
        void should_throw_when_null() {
            // given & when & then
            assertThatThrownBy(() -> new Name(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("이름이 빈 문자열이면 예외가 발생한다.")
        void should_throw_when_empty() {
            // given & when & then
            assertThatThrownBy(() -> new Name(""))
                .isInstanceOf(InvalidUserNameException.class);
        }

        @Test
        @DisplayName("이름이 2자 미만이면 예외가 발생해야 한다.")
        void should_throw_when_too_short() {
            // given & when & then
            assertThatThrownBy(() -> new Name("a"))
                .isInstanceOf(InvalidUserNameException.class);
        }

        @Test
        @DisplayName("이름이 7자 초과이면 예외가 발생해야 한다.")
        void should_throw_when_too_long() {
            // given & when & then
            assertThatThrownBy(() -> new Name("abcdefgh"))
                .isInstanceOf(InvalidUserNameException.class);
        }
    }
}
