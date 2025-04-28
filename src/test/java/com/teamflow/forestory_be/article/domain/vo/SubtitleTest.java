package com.teamflow.forestory_be.article.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.teamflow.forestory_be.article.domain.exception.InvalidSubtitleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class SubtitleTest {

    @Nested
    @DisplayName("정상 case")
    class ValidSubtitleTest {

        @Test
        @DisplayName("정상적인 부제목을 생성")
        void should_create_valid_subtitle() {
            // given & when
            Subtitle subtitle = new Subtitle("부제목입니다.");

            // then
            assertThat(subtitle).isEqualTo(new Subtitle("부제목입니다."));
        }

        @Test
        @DisplayName("부제목이 최소/최대 길이일 때 생성")
        void should_create_subtitle_when_length_is_min_or_max() {
            // given
            String minSubtitle = "a";
            String maxSubtitle = "a".repeat(50);

            // when
            Subtitle subtitleAtMin = new Subtitle(minSubtitle);
            Subtitle subtitleAtMax = new Subtitle(maxSubtitle);

            // then
            assertThat(subtitleAtMin.value()).isEqualTo(minSubtitle);
            assertThat(subtitleAtMax.value()).isEqualTo(maxSubtitle);
        }
    }

    @Nested
    @DisplayName("실패 case")
    class InvalidSubtitleTest {

        @Test
        @DisplayName("부제목이 null이면 예외가 발생")
        void should_throw_when_null() {
            // given & when & then
            assertThatThrownBy(() -> new Subtitle(null))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("부제목이 빈 문자열이면 예외가 발생")
        void should_throw_when_empty() {
            // given & when & then
            assertThatThrownBy(() -> new Subtitle(""))
                .isInstanceOf(InvalidSubtitleException.class);
        }

        @Test
        @DisplayName("부제목이 1자 미만이면 예외가 발생")
        void should_throw_when_too_short() {
            // given & when & then
            assertThatThrownBy(() -> new Subtitle(""))
                .isInstanceOf(InvalidSubtitleException.class);
        }

        @Test
        @DisplayName("부제목이 50자 초과이면 예외가 발생")
        void should_throw_when_too_long() {
            // given & when & then
            assertThatThrownBy(() -> new Subtitle("a".repeat(51)))
                .isInstanceOf(InvalidSubtitleException.class);
        }
    }
}
