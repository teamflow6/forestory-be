package com.teamflow.forestory_be.article.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.teamflow.forestory_be.article.domain.exception.InvalidContentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ContentTest {

    @Nested
    @DisplayName("정상 case")
    class ValidNameTest {

        @Test
        @DisplayName("정상적인 내용을 생성")
        void should_create_valid_content() {
            // given&when
            Content content = new Content("test");
            // then
            assertThat(content).isEqualTo(new Content("test"));

        }

        @Test
        @DisplayName("내용이 최소/최대 길이일 때 생성")
        void should_create_content_when_length_is_min_or_max() {
            // given
            String minContent = "a";
            String maxContent = "a".repeat(3000);

            // when
            Content contentAtMin = new Content(minContent);
            Content contentAtMax = new Content(maxContent);

            // then
            assertThat(contentAtMin.value()).isEqualTo(minContent);
            assertThat(contentAtMax.value()).isEqualTo(maxContent);
        }
    }

    @Nested
    @DisplayName("실패 case")
    class InvalidNameTest {

        @Test
        @DisplayName("내용이 null이면 예외가 발생")
        void should_throw_when_null() {
            //given & when &then
            assertThatThrownBy(() -> new Content(null))
                    .isInstanceOf(NullPointerException.class);

        }

        @Test
        @DisplayName("내용이 빈 문자열(1자미만)이면 예외가 발생")
        void should_throw_when_empty() {
            //given & when & then
            assertThatThrownBy(() -> new Content(""))
                    .isInstanceOf(InvalidContentException.class);
        }

        @Test
        @DisplayName("내용이 3000자 초과이면 예외가 발생")
        void should_throw_when_too_long() {
            //given & when  & then
            assertThatThrownBy(() -> new Content("a".repeat(3001)))
                    .isInstanceOf(InvalidContentException.class);

        }
    }

}



















