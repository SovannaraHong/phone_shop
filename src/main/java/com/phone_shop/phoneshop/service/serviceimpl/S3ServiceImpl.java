package com.phone_shop.phoneshop.service.serviceimpl;

import com.phone_shop.phoneshop.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;

    @Value("${aws.bucket-name}")
    private String bucketName;
    @Value("${aws.region}")
    private String region;

    @Override
    public String uploadFile(MultipartFile file, String folder) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        String key = folder + "/" + fileName;
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                // Remove .acl(...) since bucket is owner-enforced
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

        // Generate URL (public access requires bucket policy)
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
    }
}