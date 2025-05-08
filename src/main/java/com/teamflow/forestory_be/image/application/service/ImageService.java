package com.teamflow.forestory_be.image.application.service;

import com.teamflow.forestory_be.image.application.dto.DeleteImageCommand;
import com.teamflow.forestory_be.image.application.dto.UploadImageCommand;
import com.teamflow.forestory_be.image.domain.repository.ImageRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepositoryPort imageRepositoryPort;

    public String upload(UploadImageCommand command) {
        return imageRepositoryPort.upload(command.image());
    }

    public void delete(DeleteImageCommand command) {
        imageRepositoryPort.delete(command.fileName());
    }

}
