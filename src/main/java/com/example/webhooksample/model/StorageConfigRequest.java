package com.example.webhooksample.model;

import jakarta.validation.constraints.NotNull;

public record StorageConfigRequest(
        @NotNull
        Integer module
) {
}
