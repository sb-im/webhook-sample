package com.example.webhooksample.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    @NotBlank
    private String endpoint;

    @NotBlank
    private String accessKey;

    @NotBlank
    private String secretKey;

    @NotBlank
    private String bucket = "webhook-sample";

    @NotBlank
    private String region = "us-east-1";

    private String stsEndpoint;

    @Min(3600)
    private int stsDurationSeconds = 3600;

    private String stsPolicy;

    private String stsRoleArn;

    @NotBlank
    private String stsRoleSessionNamePrefix = "webhook-storage";

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStsEndpoint() {
        return hasText(stsEndpoint) ? stsEndpoint : endpoint;
    }

    public void setStsEndpoint(String stsEndpoint) {
        this.stsEndpoint = stsEndpoint;
    }

    public int getStsDurationSeconds() {
        return stsDurationSeconds;
    }

    public void setStsDurationSeconds(int stsDurationSeconds) {
        this.stsDurationSeconds = stsDurationSeconds;
    }

    public String getStsPolicy() {
        return hasText(stsPolicy) ? stsPolicy : null;
    }

    public void setStsPolicy(String stsPolicy) {
        this.stsPolicy = stsPolicy;
    }

    public String getStsRoleArn() {
        return hasText(stsRoleArn) ? stsRoleArn : null;
    }

    public void setStsRoleArn(String stsRoleArn) {
        this.stsRoleArn = stsRoleArn;
    }

    public String getStsRoleSessionNamePrefix() {
        return stsRoleSessionNamePrefix;
    }

    public void setStsRoleSessionNamePrefix(String stsRoleSessionNamePrefix) {
        this.stsRoleSessionNamePrefix = stsRoleSessionNamePrefix;
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
