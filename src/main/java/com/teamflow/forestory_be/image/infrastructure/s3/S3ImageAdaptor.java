package com.teamflow.forestory_be.image.infrastructure.s3;

import com.teamflow.forestory_be.image.domain.repository.ImageRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3ImageAdaptor implements ImageRepositoryPort {

    private final S3ImageUploader uploader;

    @Override
    public String upload(MultipartFile file) {
        return uploader.upload(file);
    }

    @Override
    public void delete(String fileName) {
        uploader.delete(fileName);
    }
}