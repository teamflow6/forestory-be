package com.teamflow.forestory_be.image.application.dto;

import org.springframework.web.multipart.MultipartFile;

public record UploadImageCommand(
        Long userId,
        MultipartFile image
) {
}
