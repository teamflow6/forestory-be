package com.teamflow.forestory_be.image.presentation.controller;

import com.teamflow.forestory_be.image.application.dto.DeleteImageCommand;
import com.teamflow.forestory_be.image.application.dto.UploadImageCommand;
import com.teamflow.forestory_be.image.application.service.ImageService;
import com.teamflow.forestory_be.image.presentation.response.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageUploadResponse> upload(
            @AuthenticationPrincipal(expression = "username") Long userId,
            @RequestPart MultipartFile image
    ) {
        UploadImageCommand command = new UploadImageCommand(userId, image);
        String url = imageService.upload(command);
        return ResponseEntity.ok(new ImageUploadResponse(url));
    }

    @DeleteMapping("/{fileName}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal(expression = "username") Long userId,
            @PathVariable String fileName
    ) {
        DeleteImageCommand command = new DeleteImageCommand(userId, fileName);
        imageService.delete(command);
        return ResponseEntity.noContent().build();
    }

}
