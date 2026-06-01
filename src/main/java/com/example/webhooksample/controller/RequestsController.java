package com.example.webhooksample.controller;

import com.example.webhooksample.common.ApiResponse;
import com.example.webhooksample.model.CommonRequest;
import com.example.webhooksample.model.StorageConfigRequest;
import com.example.webhooksample.model.StorageConfigResponse;
import com.example.webhooksample.model.WaylineFileResponse;
import com.example.webhooksample.service.StorageConfigService;
import com.example.webhooksample.service.WaylineService;
import io.minio.errors.MinioException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/requests")
public class RequestsController {

    private final StorageConfigService storageConfigService;
    private final WaylineService waylineService;

    public RequestsController(StorageConfigService storageConfigService, WaylineService waylineService) {
        this.storageConfigService = storageConfigService;
        this.waylineService = waylineService;
    }

    @PostMapping("/storage-config-get")
    public ApiResponse<StorageConfigResponse> storageConfigGet(@Valid @RequestBody CommonRequest<StorageConfigRequest> request) {
        return ApiResponse.success(storageConfigService.create(request));
    }

    @GetMapping("/flighttask-resource-get")
    public ApiResponse<WaylineFileResponse> flighttaskResourceGet(@RequestParam("event-id") String eventId)
            throws MinioException {
        return ApiResponse.success(waylineService.createSampleKmzDownload(eventId));
    }
}
