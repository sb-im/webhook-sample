package com.example.webhooksample.controller;

import com.example.webhooksample.common.ApiResponse;
import com.example.webhooksample.model.StorageConfigResponse;
import com.example.webhooksample.model.WaylineFileResponse;
import com.example.webhooksample.service.StorageConfigService;
import com.example.webhooksample.service.WaylineService;
import io.minio.errors.MinioException;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/storage-config-get")
    public ApiResponse<StorageConfigResponse> storageConfigGet(
            @RequestParam("module") Integer module,
            @RequestParam("device-sn") String deviceSn
    ) {
        return ApiResponse.success(storageConfigService.create(module, deviceSn));
    }

    @GetMapping("/flighttask-resource-get")
    public ApiResponse<WaylineFileResponse> flighttaskResourceGet(@RequestParam("event-id") String eventId)
            throws MinioException {
        return ApiResponse.success(waylineService.createSampleKmzDownload(eventId));
    }
}
