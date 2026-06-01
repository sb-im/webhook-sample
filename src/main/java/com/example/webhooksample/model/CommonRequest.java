package com.example.webhooksample.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommonRequest<T>(
        @NotNull
        Long timestamp,

        @NotNull
        Integer retry,

        @NotBlank
        String device_sn,

        @Valid
        @NotNull
        T data
) {
}
