package com.example.webhooksample.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webhooksample.common.ApiExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class OsdControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new OsdController())
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void deviceOsdReturnsSuccess() throws Exception {
        mockMvc.perform(post("/webhook/osd/device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(osdRequest()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"));
    }

    @Test
    void subDeviceOsdReturnsSuccess() throws Exception {
        mockMvc.perform(post("/webhook/osd/sub-device")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(osdRequest()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"));
    }

    private String osdRequest() {
        return """
                {
                  "timestamp": 1710000000000,
                  "retry": 0,
                  "device_sn": "xxx",
                  "data": {
                    "latitude": 22.54286,
                    "longitude": 113.95813,
                    "height": 120.5,
                    "mode_code": 3
                  }
                }
                """;
    }
}
