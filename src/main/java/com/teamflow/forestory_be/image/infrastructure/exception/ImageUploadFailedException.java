package com.teamflow.forestory_be.image.infrastructure.exception;

import com.teamflow.forestory_be.support.common.exception.CustomException;

public class ImageUploadFailedException extends CustomException {

    private static final String ERROR_CODE = "IMAGE_001";
    private static final String DEFAULT_MESSAGE = "이미지 업로드에 실패했습니다.";

    public ImageUploadFailedException() {
        super(ERROR_CODE, DEFAULT_MESSAGE);
    }

    public ImageUploadFailedException(String message) {
        super(ERROR_CODE, message);
    }
}
