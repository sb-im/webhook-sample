package com.example.webhooksample.service;

import com.example.webhooksample.config.MinioProperties;
import com.example.webhooksample.model.CommonRequest;
import com.example.webhooksample.model.StorageConfigRequest;
import com.example.webhooksample.model.StorageCredentials;
import io.minio.credentials.AssumeRoleProvider;
import io.minio.credentials.Credentials;
import io.minio.errors.MinioException;
import org.springframework.stereotype.Service;

@Service
public class MinioStsCredentialService implements StorageCredentialService {

    private final MinioProperties properties;

    public MinioStsCredentialService(MinioProperties properties) {
        this.properties = properties;
    }

    @Override
    public StorageCredentials create(CommonRequest<StorageConfigRequest> request, String objectKeyPrefix) {
        Credentials credentials = assumeRole(request, objectKeyPrefix);
        return new StorageCredentials(
                credentials.accessKey(),
                credentials.secretKey(),
                properties.getStsDurationSeconds(),
                credentials.sessionToken()
        );
    }

    private Credentials assumeRole(CommonRequest<StorageConfigRequest> request, String objectKeyPrefix) {
        try {
            AssumeRoleProvider provider = new AssumeRoleProvider(
                    properties.getStsEndpoint(),
                    properties.getAccessKey(),
                    properties.getSecretKey(),
                    properties.getStsDurationSeconds(),
                    properties.getStsPolicy(),
                    properties.getRegion(),
                    properties.getStsRoleArn(),
                    roleSessionName(request, objectKeyPrefix),
                    null,
                    null
            );
            return provider.fetch();
        } catch (MinioException exception) {
            throw new IllegalStateException("Unable to create MinIO STS assume-role provider", exception);
        }
    }

    private String roleSessionName(CommonRequest<StorageConfigRequest> request, String objectKeyPrefix) {
        String raw = String.join("-", properties.getStsRoleSessionNamePrefix(), String.valueOf(request.data().module()), request.device_sn(), objectKeyPrefix);
        String normalized = raw.replaceAll("[^A-Za-z0-9+=,.@-]", "-");
        return normalized.length() > 64 ? normalized.substring(0, 64) : normalized;
    }
}
