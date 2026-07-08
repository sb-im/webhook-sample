package com.example.webhooksample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FileUploadProgressFile(
        String key,

        @Valid
        FileUploadProgressStep progress
) {
}
