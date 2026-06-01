package com.example.webhooksample.model;

public record StorageCredentials(
        String access_key_id,
        String access_key_secret,
        int expire,
        String security_token
) {
}
