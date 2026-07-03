package com.example.webhooksample.controller;

import com.example.webhooksample.common.ApiResponse;
import com.example.webhooksample.model.CommonRequest;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/status")
public class StatusController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusController.class);

    @PostMapping("/update-topo")
    public ApiResponse<Void> updateTopo(@Valid @RequestBody CommonRequest<Map<String, Object>> request) {
        LOGGER.info("Webhook event=update-topo device_sn={} status=online", request.device_sn());
        for (Object subDevice : subDevices(request.data())) {
            if (subDevice instanceof Map<?, ?> subDeviceData) {
                LOGGER.info(
                        "Webhook event=update-topo device_sn={} sub_device_sn={} status=online",
                        request.device_sn(),
                        subDeviceData.get("sn")
                );
            }
        }
        return ApiResponse.success(null);
    }

    private Iterable<?> subDevices(Map<String, Object> data) {
        Object subDevices = data.get("sub_devices");
        if (subDevices instanceof Iterable<?> iterable) {
            return iterable;
        }
        return Collections.emptyList();
    }
}
