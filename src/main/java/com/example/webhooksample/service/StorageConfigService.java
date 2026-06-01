package com.example.webhooksample.service;

import com.example.webhooksample.config.MinioProperties;
import com.example.webhooksample.model.CommonRequest;
import com.example.webhooksample.model.StorageConfigRequest;
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

    public StorageConfigResponse create(CommonRequest<StorageConfigRequest> request) {
        String objectKeyPrefix = UUID.randomUUID().toString();
        StorageCredentials credentials = credentialService.create(request, objectKeyPrefix);
        return new StorageConfigResponse(
                request.device_sn(),
                properties.getBucket(),
                credentials,
                properties.getEndpoint(),
                objectKeyPrefix,
                "minio",
                properties.getRegion()
        );
    }
}
