package kwonjh0406.sns.application.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImageToS3(MultipartFile image) throws IOException {
        // 이미지 원본 파일 이름
        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalArgumentException("Original filename must not be null");
        }
        // 이미지 확장자
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // UUID + 이미지 원본 이름으로 S3 객체 키 생성
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + "_" + originalFilename;

        byte[] bytes = image.getBytes();

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(s3FileName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .contentType("image/" + extension)
                .build();

        try {
            s3Client.putObject(putRequest, RequestBody.fromBytes(bytes));
        } catch (Exception e) {
            throw new IOException("Failed to upload image to S3", e);
        }

        return s3Client.utilities()
                .getUrl(GetUrlRequest.builder().bucket(bucket).key(s3FileName).build())
                .toString();
    }


    public void deleteImageFromS3(String imageUrl) {
        String key = getKeyFromImageAddress(imageUrl);
        try {
            DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete image from S3: " + key, e);
        }
    }

    private String getKeyFromImageAddress(String imageUrl) {
        try {
            URI uri = new URI(imageUrl);
            String path = uri.getPath();
            String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
            return decodedPath.startsWith("/") ? decodedPath.substring(1) : decodedPath;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid image URL format: " + imageUrl, e);
        }
    }
}
