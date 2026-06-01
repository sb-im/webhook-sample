package com.example.webhooksample.controller;

import com.example.webhooksample.common.ApiResponse;
import com.example.webhooksample.model.CommonRequest;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/osd")
public class OsdController {

    @PostMapping("/device")
    public ApiResponse<Void> deviceOsd(@Valid @RequestBody CommonRequest<Map<String, Object>> request) {
        return ApiResponse.success(null);
    }

    @PostMapping("/sub-device")
    public ApiResponse<Void> subDeviceOsd(@Valid @RequestBody CommonRequest<Map<String, Object>> request) {
        return ApiResponse.success(null);
    }
}
