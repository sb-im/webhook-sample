package com.example.webhooksample.service;

import com.example.webhooksample.config.MinioProperties;
import com.example.webhooksample.model.StorageConfigResponse;
import com.example.webhooksample.model.StorageCredentials;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class StorageConfigService {

    private final MinioProperties properties;
    private final StorageCredentialService credentialService;

    public StorageConfigService(MinioProperties properties, StorageCredentialService credentialService) {
        this.properties = properties;
        this.credentialService = credentialService;
    }

    public StorageConfigResponse create(Integer module, String deviceSn) {
        String objectKeyPrefix = UUID.randomUUID().toString();
        StorageCredentials credentials = credentialService.create(module, deviceSn, objectKeyPrefix);
        return new StorageConfigResponse(
                deviceSn,
                properties.getBucket(),
                credentials,
                properties.getEndpoint(),
                objectKeyPrefix,
                "minio",
                properties.getRegion()
        );
    }
}
