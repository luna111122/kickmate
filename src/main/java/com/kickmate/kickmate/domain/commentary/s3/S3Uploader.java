package com.kickmate.kickmate.domain.commentary.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.base-url}")
    private String baseUrl;
    // 예: https://{bucket}.s3.ap-northeast-2.amazonaws.com

    public String upload(String key, byte[] bytes, String contentType) {

        log.info("[S3] upload start | bucket={} key={} contentType={} bytes={}",
                bucket, key, contentType, bytes != null ? bytes.length : 0);

        try {
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

            String url = baseUrl + "/" + key;
            log.info("[S3] upload success | url={}", url);
            return url;

        } catch (Exception e) {
            log.error("[S3] upload failed | bucket={} key={}", bucket, key, e);
            throw e; // 기존 흐름 유지
        }
    }
}
