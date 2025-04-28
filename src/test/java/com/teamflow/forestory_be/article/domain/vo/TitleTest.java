package com.teamflow.forestory_be.article.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.teamflow.forestory_be.article.domain.exception.InvalidTitleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TitleTest {

    @Nested
    @DisplayName("정상 case")
    class ValidTitleTest {

        @Test
        @DisplayName("정상적인 제목을 생성")
        void should_create_valid_title() {
            // given & when
            Title title = new Title("제목입니다.");

            // then
            assertThat(title).isEqualTo(new Title("제목입니다."));
        }

        @Test
        @DisplayName("제목이 최소/최대 길이일 때 생성")
        void should_create_title_when_length_is_min_or_max() {
            // given
            String minTitle = "a";
            String maxTitle = "a".repeat(50);

            // when
            Title titleAtMin = new Title(minTitle);
            Title titleAtMax = new Title(maxTitle);

            // then
            assertThat(titleAtMin.value()).isEqualTo(minTitle);
            assertThat(titleAtMax.value()).isEqualTo(maxTitle);
        }
    }

    @Nested
    @DisplayName("실패 case")
    class InvalidTitleTest {

        @Test
        @DisplayName("제목이 null이면 예외가 발생")
        void should_throw_when_null() {
            // given & when & then
            assertThatThrownBy(() -> new Title(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("제목이 빈 문자열이면 예외가 발생")
        void should_throw_when_empty() {
            // given & when & then
            assertThatThrownBy(() -> new Title(""))
                .isInstanceOf(InvalidTitleException.class);
        }

        @Test
        @DisplayName("제목이 1자 미만이면 예외가 발생")
        void should_throw_when_too_short() {
            // given & when & then
            assertThatThrownBy(() -> new Title(""))
                .isInstanceOf(InvalidTitleException.class);
        }

        @Test
        @DisplayName("제목이 50자 초과이면 예외가 발생")
        void should_throw_when_too_long() {
            // given & when & then
            assertThatThrownBy(() -> new Title("a".repeat(51)))
                .isInstanceOf(InvalidTitleException.class);
        }
    }
}
