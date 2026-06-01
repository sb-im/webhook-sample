package com.example.webhooksample.service;

import com.example.webhooksample.model.CommonRequest;
import com.example.webhooksample.model.StorageConfigRequest;
import com.example.webhooksample.model.StorageCredentials;

public interface StorageCredentialService {

    StorageCredentials create(CommonRequest<StorageConfigRequest> request, String objectKeyPrefix);
}
