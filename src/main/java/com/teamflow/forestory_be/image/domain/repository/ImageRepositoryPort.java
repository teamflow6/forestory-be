package com.teamflow.forestory_be.image.domain.repository;

import org.springframework.web.multipart.MultipartFile;

public interface ImageRepositoryPort {
    String upload(MultipartFile file);

    void delete(String filenName);
}
