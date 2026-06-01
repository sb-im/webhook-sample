package com.example.webhooksample.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.mock.env.MockEnvironment;

class MinioPropertiesTest {

    @Test
    void bindsMinioProperties() {
        MockEnvironment environment = new MockEnvironment()
                .withProperty("minio.endpoint", "http://localhost:9000")
                .withProperty("minio.access-key", "minioadmin")
                .withProperty("minio.secret-key", "minioadmin")
                .withProperty("minio.bucket", "uploads")
                .withProperty("minio.region", "us-east-1")
                .withProperty("minio.sts-endpoint", "http://localhost:9000")
                .withProperty("minio.sts-duration-seconds", "3600")
                .withProperty("minio.sts-role-session-name-prefix", "webhook-storage");

        MinioProperties properties = Binder.get(environment)
                .bind("minio", Bindable.of(MinioProperties.class))
                .orElseThrow(() -> new AssertionError("minio properties were not bound"));

        assertThat(properties.getEndpoint()).isEqualTo("http://localhost:9000");
        assertThat(properties.getAccessKey()).isEqualTo("minioadmin");
        assertThat(properties.getSecretKey()).isEqualTo("minioadmin");
        assertThat(properties.getBucket()).isEqualTo("uploads");
        assertThat(properties.getRegion()).isEqualTo("us-east-1");
        assertThat(properties.getStsEndpoint()).isEqualTo("http://localhost:9000");
        assertThat(properties.getStsDurationSeconds()).isEqualTo(3600);
        assertThat(properties.getStsRoleSessionNamePrefix()).isEqualTo("webhook-storage");
    }
}
