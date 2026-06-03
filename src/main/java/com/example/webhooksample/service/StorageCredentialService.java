package com.example.webhooksample.service;

import com.example.webhooksample.model.StorageCredentials;

public interface StorageCredentialService {

    StorageCredentials create(Integer module, String deviceSn, String objectKeyPrefix);
}
