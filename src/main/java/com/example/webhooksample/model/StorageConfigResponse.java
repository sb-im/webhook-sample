package com.example.webhooksample.model;

public record StorageConfigResponse(
        String device_sn,
        String bucket,
        StorageCredentials credentials,
        String endpoint,
        String object_key_prefix,
        String provider,
        String region
) {
}
