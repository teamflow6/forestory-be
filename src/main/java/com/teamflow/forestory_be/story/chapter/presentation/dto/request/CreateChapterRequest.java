package com.teamflow.forestory_be.story.chapter.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CreateChapterRequest(

        @NotNull(message = "시리즈 ID는 필수입니다.")
        Long seriesId,

        @NotBlank(message = "제목은 필수입니다.")
        String chapterTitle,

        @NotBlank(message = "부제목은 필수입니다.")
        String chapterSubtitle,

        @NotBlank(message = "본문 내용은 필수입니다.")
        String chapterBody,

        @Size(max = 3, message = "이미지는 최대 3개까지 등록할 수 있습니다.")
        List<String> imageUrls,

        @NotBlank(message = "상태는 필수입니다.")
        String status
) {
}
