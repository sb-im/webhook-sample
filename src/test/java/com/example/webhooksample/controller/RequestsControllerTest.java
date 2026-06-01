package com.example.webhooksample.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webhooksample.common.ApiExceptionHandler;
import com.example.webhooksample.model.StorageConfigResponse;
import com.example.webhooksample.model.StorageCredentials;
import com.example.webhooksample.model.WaylineFileResponse;
import com.example.webhooksample.service.StorageConfigService;
import com.example.webhooksample.service.WaylineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RequestsControllerTest {

    @Mock
    private StorageConfigService storageConfigService;

    @Mock
    private WaylineService waylineService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new RequestsController(storageConfigService, waylineService))
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void storageConfigGetReturnsMinioStorageConfig() throws Exception {
        when(storageConfigService.create(any())).thenReturn(new StorageConfigResponse(
                "xxx",
                "bucket_name",
                new StorageCredentials(
                        "access_key_id",
                        "access_key_secret",
                        3600,
                        "security_token"
                ),
                "http://localhost:9000",
                "b4cfaae6-bd9d-4cd0-8472-63b608c3c581",
                "minio",
                "us-east-1"
        ));

        mockMvc.perform(post("/webhook/requests/storage-config-get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "timestamp": 1710000000000,
                                  "retry": 0,
                                  "device_sn": "xxx",
                                  "data": {
                                    "module": 1
                                  }
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.device_sn").value("xxx"))
                .andExpect(jsonPath("$.data.bucket").value("bucket_name"))
                .andExpect(jsonPath("$.data.credentials.access_key_id").value("access_key_id"))
                .andExpect(jsonPath("$.data.credentials.access_key_secret").value("access_key_secret"))
                .andExpect(jsonPath("$.data.credentials.expire").value(3600))
                .andExpect(jsonPath("$.data.credentials.security_token").value("security_token"))
                .andExpect(jsonPath("$.data.endpoint").value("http://localhost:9000"))
                .andExpect(jsonPath("$.data.object_key_prefix").value("b4cfaae6-bd9d-4cd0-8472-63b608c3c581"))
                .andExpect(jsonPath("$.data.provider").value("minio"))
                .andExpect(jsonPath("$.data.region").value("us-east-1"));
    }

    @Test
    void flighttaskResourceGetReturnsSampleKmzDownload() throws Exception {
        when(waylineService.createSampleKmzDownload("event-001"))
                .thenReturn(new WaylineFileResponse("aca4fd206d75c05db92f6a97b32d2542", "https://example.com/sample.kmz?X-Amz-Expires=3600"));

        mockMvc.perform(get("/webhook/requests/flighttask-resource-get")
                        .param("event-id", "event-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.fingerprint").value("aca4fd206d75c05db92f6a97b32d2542"))
                .andExpect(jsonPath("$.data.url").value("https://example.com/sample.kmz?X-Amz-Expires=3600"));
    }
}
