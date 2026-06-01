package com.example.webhooksample.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webhooksample.common.ApiExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class EventsControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new EventsController())
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/webhook/events/cover-open",
            "/webhook/events/cover-close",
            "/webhook/events/charge-open",
            "/webhook/events/charge-close",
            "/webhook/events/drone-open",
            "/webhook/events/drone-close"
    })
    void eventsReturnSuccess(String path) throws Exception {
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(progressRequest()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/webhook/events/device/hms",
            "/webhook/events/sub-device/hms"
    })
    void hmsEventsReturnSuccess(String path) throws Exception {
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(hmsRequest()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/webhook/events/file-upload-callback"
    })
    void fileUploadCallbackReturnsSuccess(String path) throws Exception {
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fileUploadCallbackRequest()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"));
    }

    private String progressRequest() {
        return """
                {
                  "timestamp": 1710000000000,
                  "retry": 0,
                  "device_sn": "xxx",
                  "data": {
                    "event_id": "event-001",
                    "output": {
                      "progress": {
                        "current_step": 1,
                        "percent": 80,
                        "step_key": "cover",
                        "step_result": 0,
                        "total_steps": 1
                      },
                      "status": "running"
                    },
                    "result": 0,
                    "result_message": "ok"
                  }
                }
                """;
    }

    private String fileUploadCallbackRequest() {
        return """
                {
                  "timestamp": 1654070968655,
                  "retry": 0,
                  "device_sn": "xxx",
                  "data": {
                    "event_id": "xxx",
                    "file": {
                      "cloud_to_cloud_id": "DEFAULT",
                      "ext": {
                        "drone_model_key": "0-67-0",
                        "flight_id": "xxx",
                        "is_original": true,
                        "payload_model_key": "0-67-0"
                      },
                      "metadata": {
                        "absolute_altitude": 56.311,
                        "create_time": "2021-05-10 16:04:20",
                        "gimbal_yaw_degree": "-91.40",
                        "relative_altitude": 41.124,
                        "shoot_position": {
                          "lat": 22.1,
                          "lng": 122.5
                        }
                      },
                      "name": "dog.jpeg",
                      "object_key": "object_key",
                      "path": "xxx"
                    }
                  }
                }
                """;
    }

    private String hmsRequest() {
        return """
                {
                  "timestamp": 1710000000000,
                  "retry": 0,
                  "device_sn": "7CTXM9600B01RW",
                  "data": {
                    "list": [
                      {
                        "args": {
                          "component_index": 0,
                          "sensor_index": 0
                        },
                        "code": "0x16100083",
                        "device_type": "0-67-0",
                        "imminent": 1,
                        "in_the_sky": 0,
                        "level": 2,
                        "module": 3,
                        "text_key": "fpv_tip_0x16100083",
                        "message": "电池安装不到位，请用力将电池推到底"
                      }
                    ]
                  }
                }
                """;
    }
}
