package com.teamflow.forestory_be.story.chapter.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateChapterRequest(

        @NotNull(message = "시리즈 ID는 필수입니다.")
        Long seriesId,

        @NotBlank(message = "제목은 필수입니다.")
        String title,

        @NotBlank(message = "부제목은 필수입니다.")
        String subtitle,

        @NotBlank(message = "본문은 필수입니다.")
        String body,

        @NotBlank(message = "상태는 필수입니다.")
        String status,

        List<String> imageUrls,

        @NotBlank(message = "회차 번호는 필수입니다.")
        String chapterNumber

) {
}
