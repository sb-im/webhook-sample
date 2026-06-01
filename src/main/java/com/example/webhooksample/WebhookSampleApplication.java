package com.example.webhooksample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class WebhookSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebhookSampleApplication.class, args);
    }
}
