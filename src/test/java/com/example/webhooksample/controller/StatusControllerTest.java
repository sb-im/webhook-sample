package com.example.webhooksample.controller;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webhooksample.WebhookSampleApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = WebhookSampleApplication.class)
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @MethodSource("topologyRequests")
    void updateTopoReturnsSuccess(String requestBody) throws Exception {
        mockMvc.perform(post("/webhook/status/update-topo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("x-user-token", "user-token")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data").value(nullValue()));
    }

    @Test
    void updateTopoLogsOnlyDeviceOnlineWhenSubDevicesIsEmpty(CapturedOutput output) throws Exception {
        performUpdateTopo(noSubDeviceTopologyRequest());

        assertTrue(output.toString().contains("Webhook event=update-topo device_sn=DOCK001 status=online"));
        assertFalse(output.toString().contains("sub_device_sn="));
    }

    @Test
    void updateTopoLogsDeviceAndSubDeviceOnlineWhenSubDevicesExists(CapturedOutput output) throws Exception {
        performUpdateTopo(topologyWithSubDeviceRequest());

        assertTrue(output.toString().contains("Webhook event=update-topo device_sn=DOCK001 status=online"));
        assertTrue(output.toString().contains(
                "Webhook event=update-topo device_sn=DOCK001 sub_device_sn=DRONE001 status=online"
        ));
    }

    private void performUpdateTopo(String requestBody) throws Exception {
        mockMvc.perform(post("/webhook/status/update-topo")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-user-token", "user-token")
                .content(requestBody));
    }

    private static String[] topologyRequests() {
        return new String[] {
                topologyWithSubDeviceRequest(),
                noSubDeviceTopologyRequest()
        };
    }

    private static String topologyWithSubDeviceRequest() {
        return """
                {
                  "timestamp": 1710000000000,
                  "retry": 0,
                  "device_sn": "DOCK001",
                  "data": {
                    "domain": "3",
                    "type": 119,
                    "sub_type": 0,
                    "device_secret": "secret",
                    "nonce": "nonce",
                    "thing_version": "1.1.2",
                    "sub_devices": [
                      {
                        "sn": "DRONE001",
                        "domain": "0",
                        "type": 60,
                        "sub_type": 0,
                        "device_secret": "sub-secret",
                        "nonce": "sub-nonce",
                        "thing_version": "1.1.2"
                      }
                    ]
                  }
                }
                """;
    }

    private static String noSubDeviceTopologyRequest() {
        return """
                {
                  "timestamp": 1710000000000,
                  "retry": 0,
                  "device_sn": "DOCK001",
                  "data": {
                    "domain": "3",
                    "type": 119,
                    "sub_type": 0,
                    "device_secret": "secret",
                    "nonce": "nonce",
                    "thing_version": "1.1.2",
                    "sub_devices": []
                  }
                }
                """;
    }
}
