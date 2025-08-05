package com.teamflow.forestory_be.image.presentation.controller;

import com.teamflow.forestory_be.image.application.dto.DeleteImageCommand;
import com.teamflow.forestory_be.image.application.dto.UploadImageCommand;
import com.teamflow.forestory_be.image.application.service.ImageService;
import com.teamflow.forestory_be.image.presentation.response.ImageUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(consumes = "multipart/form-data")
    @Operation(
            summary = "이미지 업로드",
            description = "MultipartFile 형식의 이미지를 업로드하고, S3 URL을 반환합니다.",
            security = @SecurityRequirement(name = "AccessToken")
    )
    public ResponseEntity<ImageUploadResponse> upload(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestPart MultipartFile image
    ) {
        UploadImageCommand command = new UploadImageCommand(userId, image);
        String url = imageService.upload(command);
        return ResponseEntity.ok(new ImageUploadResponse(url));
    }

    @DeleteMapping("/{fileName}")
    @Operation(
            summary = "이미지 삭제",
            description = "파일명을 기반으로 이미지를 삭제합니다.",
            security = @SecurityRequirement(name = "AccessToken")
    )
    public ResponseEntity<Void> delete(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @PathVariable String fileName
    ) {
        DeleteImageCommand command = new DeleteImageCommand(userId, fileName);
        imageService.delete(command);
        return ResponseEntity.noContent().build();
    }
}
