package com.example.webhooksample.model;

import jakarta.validation.Valid;
import java.util.List;

public record HmsData(
        @Valid
        List<HmsMessage> list
) {
}
