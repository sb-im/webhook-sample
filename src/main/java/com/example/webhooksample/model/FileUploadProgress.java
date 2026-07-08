package com.example.webhooksample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FileUploadProgress(
        String event_id,

        @Valid
        FileUploadProgressOutput output
) {
}
