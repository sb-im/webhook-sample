package com.example.webhooksample.service;

import com.example.webhooksample.config.MinioProperties;
import com.example.webhooksample.model.WaylineFileResponse;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.Http;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import org.springframework.stereotype.Service;

@Service
public class WaylineService {

    private static final String SAMPLE_WAYLINE_OBJECT = "sample.kmz";
    private static final int DOWNLOAD_URL_EXPIRY_SECONDS = 3600;

    private final MinioClient minioClient;
    private final MinioProperties properties;

    public WaylineService(MinioClient minioClient, MinioProperties properties) {
        this.minioClient = minioClient;
        this.properties = properties;
    }

    public WaylineFileResponse createSampleKmzDownload(String eventId) throws MinioException {
        if (eventId == null || eventId.isBlank()) {
            throw new IllegalArgumentException("event_id must not be blank");
        }

        String fingerprint = md5(SAMPLE_WAYLINE_OBJECT);
        String url = minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Http.Method.GET)
                        .bucket(properties.getBucket())
                        .object(SAMPLE_WAYLINE_OBJECT)
                        .expiry(DOWNLOAD_URL_EXPIRY_SECONDS)
                        .build()
        );
        return new WaylineFileResponse(fingerprint, url);
    }

    private String md5(String objectName) throws MinioException {
        MessageDigest digest = md5Digest();
        byte[] buffer = new byte[8192];

        try (GetObjectResponse response = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(properties.getBucket())
                        .object(objectName)
                        .build()
        )) {
            int read;
            while ((read = response.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read MinIO object: " + objectName, exception);
        }

        return HexFormat.of().formatHex(digest.digest());
    }

    private MessageDigest md5Digest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("MD5 digest is not available", exception);
        }
    }
}
