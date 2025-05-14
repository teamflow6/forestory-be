package com.teamflow.forestory_be.image.infrastructure.s3;

import com.teamflow.forestory_be.image.infrastructure.exception.ImageUploadFailedException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3ImageUploader {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    public String upload(MultipartFile file) {
        String fileName = generateUniqueFileName(file.getOriginalFilename());

        try (InputStream is = file.getInputStream()) {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(is, file.getSize()));

            GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();

            return s3Client.utilities().getUrl(getUrlRequest).toString();
        } catch (IOException e) {
            throw new ImageUploadFailedException("이미지 업로드 중 오류가 발생했습니다.");
        }
    }

    public void delete(String fileName) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();

            s3Client.deleteObject(request);
        } catch (Exception e) {
            throw new ImageUploadFailedException("이미지 삭제 중 오류가 발생했습니다.");
        }
    }


    private String generateUniqueFileName(String originalName) {
        return UUID.randomUUID() + "_" + originalName;
    }

}

