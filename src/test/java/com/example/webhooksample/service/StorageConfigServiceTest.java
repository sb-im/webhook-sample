package com.example.webhooksample.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.webhooksample.config.MinioProperties;
import com.example.webhooksample.model.CommonRequest;
import com.example.webhooksample.model.StorageConfigRequest;
import com.example.webhooksample.model.StorageConfigResponse;
import com.example.webhooksample.model.StorageCredentials;
import org.junit.jupiter.api.Test;

class StorageConfigServiceTest {

    @Test
    void createsMinioStorageConfigFromPropertiesAndStsCredentials() {
        MinioProperties properties = new MinioProperties();
        properties.setEndpoint("http://localhost:9000");
        properties.setAccessKey("minioadmin");
        properties.setSecretKey("minioadmin");
        properties.setBucket("uploads");
        properties.setRegion("us-east-1");

        StorageCredentialService credentialService = (request, objectKeyPrefix) -> new StorageCredentials(
                "temporary-access-key",
                "temporary-secret-key",
                3600,
                "temporary-session-token"
        );
        StorageConfigService service = new StorageConfigService(properties, credentialService);

        StorageConfigResponse response = service.create(new CommonRequest<>(
                1710000000000L,
                0,
                "device-001",
                new StorageConfigRequest(1)
        ));

        assertThat(response.device_sn()).isEqualTo("device-001");
        assertThat(response.bucket()).isEqualTo("uploads");
        assertThat(response.endpoint()).isEqualTo("http://localhost:9000");
        assertThat(response.provider()).isEqualTo("minio");
        assertThat(response.region()).isEqualTo("us-east-1");
        assertThat(response.object_key_prefix()).isNotBlank();
        assertThat(response.credentials().access_key_id()).isEqualTo("temporary-access-key");
        assertThat(response.credentials().access_key_secret()).isEqualTo("temporary-secret-key");
        assertThat(response.credentials().expire()).isEqualTo(3600);
        assertThat(response.credentials().security_token()).isEqualTo("temporary-session-token");
    }
}
