package com.kickmate.kickmate.domain.commentary.s3;

import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.base-url}")
    private String baseUrl;
    // 예: https://{bucket}.s3.ap-northeast-2.amazonaws.com

    public String upload(String key, byte[] bytes, String contentType) {

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .contentLength((long) bytes.length)
                .build();

        s3Client.putObject(
                request,
                RequestBody.fromBytes(bytes)
        );

        return baseUrl + "/" + key;
    }
}
