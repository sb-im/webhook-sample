package com.example.webhooksample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FlighttaskProgressOutput(
        @Valid
        FlighttaskProgressStep progress,

        String status
) {
}
