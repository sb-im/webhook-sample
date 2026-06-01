package com.example.webhooksample.model;

public record CommonProgress(
        String event_id,
        CommonProgressOutput output,
        Integer result,
        String result_message
) {
}
