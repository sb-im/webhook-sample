package com.example.webhooksample.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommonProgressStep(
        @NotNull
        Integer current_step,

        @NotNull
        Integer percent,

        @NotBlank
        String step_key,

        @NotNull
        Integer step_result,

        @NotNull
        Integer total_steps
) {
}
