package com.example.webhooksample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FlighttaskProgress(
        String event_id,

        @Valid
        FlighttaskProgressOutput output,

        Integer result,
        String result_message,
        String message
) {
}
