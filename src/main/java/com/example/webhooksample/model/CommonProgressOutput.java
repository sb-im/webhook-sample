package com.example.webhooksample.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommonProgressOutput(
        @Valid
        @NotNull
        CommonProgressStep progress,

        @NotBlank
        String status
) {
}
