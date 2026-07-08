package com.example.webhooksample.controller;

import com.example.webhooksample.common.ApiResponse;
import com.example.webhooksample.model.CommonProgress;
import com.example.webhooksample.model.CommonRequest;
import com.example.webhooksample.model.FileUploadCallback;
import com.example.webhooksample.model.FileUploadProgress;
import com.example.webhooksample.model.FileUploadProgressFile;
import com.example.webhooksample.model.FlighttaskProgress;
import com.example.webhooksample.model.HmsData;
import com.example.webhooksample.model.HmsMessage;
import jakarta.validation.Valid;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/events")
public class EventsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsController.class);

    @PostMapping("/cover-open")
    public ApiResponse<Void> coverOpen(@Valid @RequestBody CommonRequest<CommonProgress> request) {
        logProgress("cover-open", request.data());
        return ApiResponse.success(null);
    }

    @PostMapping("/cover-close")
    public ApiResponse<Void> coverClose(@Valid @RequestBody CommonRequest<CommonProgress> request) {
        logProgress("cover-close", request.data());
        return ApiResponse.success(null);
    }

    @PostMapping("/charge-open")
    public ApiResponse<Void> chargeOpen(@Valid @RequestBody CommonRequest<CommonProgress> request) {
        logProgress("charge-open", request.data());
        return ApiResponse.success(null);
    }

    @PostMapping("/charge-close")
    public ApiResponse<Void> chargeClose(@Valid @RequestBody CommonRequest<CommonProgress> request) {
        logProgress("charge-close", request.data());
        return ApiResponse.success(null);
    }

    @PostMapping("/drone-open")
    public ApiResponse<Void> droneOpen(@Valid @RequestBody CommonRequest<CommonProgress> request) {
        logProgress("drone-open", request.data());
        return ApiResponse.success(null);
    }

    @PostMapping("/drone-close")
    public ApiResponse<Void> droneClose(@Valid @RequestBody CommonRequest<CommonProgress> request) {
        logProgress("drone-close", request.data());
        return ApiResponse.success(null);
    }

    @PostMapping("/device/hms")
    public ApiResponse<Void> deviceHms(@Valid @RequestBody CommonRequest<HmsData> request) {
        logHmsMessages("device-hms", request);
        return ApiResponse.success(null);
    }

    @PostMapping("/sub-device/hms")
    public ApiResponse<Void> subDeviceHms(@Valid @RequestBody CommonRequest<HmsData> request) {
        logHmsMessages("sub-device-hms", request);
        return ApiResponse.success(null);
    }

    @PostMapping("/file-upload-callback")
    public ApiResponse<Void> fileUploadCallback(@Valid @RequestBody CommonRequest<FileUploadCallback> request) {
        LOGGER.info(
                "Webhook event=file-upload-callback event_id={} name={}",
                request.data().event_id(),
                request.data().file().name()
        );
        return ApiResponse.success(null);
    }

    @PostMapping("/fileupload-progress")
    public ApiResponse<Void> fileUploadProgress(@Valid @RequestBody CommonRequest<FileUploadProgress> request) {
        for (FileUploadProgressFile file : fileUploadProgressFiles(request.data())) {
            LOGGER.info(
                    "Webhook event=fileupload-progress event_id={} key={} progress={}",
                    request.data().event_id(),
                    file.key(),
                    fileUploadProgress(file)
            );
        }
        return ApiResponse.success(null);
    }

    @PostMapping("/flighttask-progress")
    public ApiResponse<Void> flighttaskProgress(@Valid @RequestBody CommonRequest<FlighttaskProgress> request) {
        FlighttaskProgress data = request.data();
        if (Integer.valueOf(0).equals(data.result())) {
            LOGGER.info(
                    "Webhook event=flighttask-progress event_id={} percent={} status={}",
                    data.event_id(),
                    flighttaskPercent(data),
                    flighttaskStatus(data)
            );
        } else {
            LOGGER.info(
                    "Webhook event=flighttask-progress event_id={} percent={} status={} message={}",
                    data.event_id(),
                    flighttaskPercent(data),
                    flighttaskStatus(data),
                    flighttaskMessage(data)
            );
        }
        return ApiResponse.success(null);
    }

    private Iterable<FileUploadProgressFile> fileUploadProgressFiles(FileUploadProgress data) {
        if (data.output() == null || data.output().ext() == null || data.output().ext().files() == null) {
            return Collections.emptyList();
        }
        return data.output().ext().files();
    }

    private Integer fileUploadProgress(FileUploadProgressFile file) {
        if (file.progress() == null) {
            return null;
        }
        return file.progress().progress();
    }

    private void logProgress(String event, CommonProgress request) {
        LOGGER.info(
                "Webhook event={} event_id={} result={} percent={} result_message={}",
                event,
                request.event_id(),
                request.result(),
                request.output().progress().percent(),
                request.result_message()
        );
    }

    private Integer flighttaskPercent(FlighttaskProgress data) {
        if (data.output() == null || data.output().progress() == null) {
            return null;
        }
        return data.output().progress().percent();
    }

    private String flighttaskStatus(FlighttaskProgress data) {
        if (data.output() == null) {
            return null;
        }
        return data.output().status();
    }

    private String flighttaskMessage(FlighttaskProgress data) {
        if (data.result_message() != null && !data.result_message().isBlank()) {
            return data.result_message();
        }
        return data.message();
    }

    private void logHmsMessages(String event, CommonRequest<HmsData> request) {
        for (HmsMessage message : hmsMessages(request.data())) {
            LOGGER.info(
                    "Webhook event={} device_sn={} message={}",
                    event,
                    request.device_sn(),
                    message.message()
            );
        }
    }

    private Iterable<HmsMessage> hmsMessages(HmsData data) {
        if (data.list() == null) {
            return Collections.emptyList();
        }
        return data.list();
    }
}
