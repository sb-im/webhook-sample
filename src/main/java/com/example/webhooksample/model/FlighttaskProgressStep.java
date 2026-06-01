package com.example.webhooksample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FlighttaskProgressStep(
        Integer percent
) {
}
