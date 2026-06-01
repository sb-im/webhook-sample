# Webhook Sample

Spring Boot 4 webhook service with MinIO STS credential vending, file logging, Gradle dependency management, and GraalVM Native Image support.

## Requirements

- JDK 25 for the configured Java toolchain
- GraalVM 25 or newer for `nativeCompile`
- A MinIO-compatible endpoint for `/webhook/requests/storage-config-get`

## Configuration

The app listens on port `8181` and uses local MinIO defaults that can be overridden with environment variables:

```bash
export MINIO_ENDPOINT=http://localhost:9000
export MINIO_ACCESS_KEY=minioadmin
export MINIO_SECRET_KEY=minioadmin
export MINIO_BUCKET=webhook-sample
export MINIO_REGION=us-east-1
export MINIO_STS_DURATION_SECONDS=3600
```

Logs are written to `logs/webhook-sample.log`, rolled by size and date, and retained for 7 days.

## Run

```bash
./gradlew bootRun
```

The Gradle build defaults to Java 25. On a machine that only needs JVM tests and has an older supported JDK, override the toolchain explicitly:

```bash
./gradlew -PjavaVersion=18 test
```

Storage config endpoint:

```bash
curl -sS -X POST http://localhost:8181/webhook/requests/storage-config-get \
  -H 'Content-Type: application/json' \
  -d '{"timestamp":1710000000000,"retry":0,"device_sn":"xxx","data":{"module":1}}'
```

## Native Image

```bash
./gradlew collectReachabilityMetadata
./gradlew nativeCompile
./build/native/nativeCompile/webhook-sample
```
