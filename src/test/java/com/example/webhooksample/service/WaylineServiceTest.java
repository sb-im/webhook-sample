package com.example.webhooksample.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import com.example.webhooksample.config.MinioProperties;
import com.example.webhooksample.model.WaylineFileResponse;
import io.minio.GetObjectResponse;
import io.minio.Http;
import io.minio.MinioClient;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import okhttp3.Headers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WaylineServiceTest {

    @Mock
    private MinioClient minioClient;

    @Test
    void createsSampleKmzDownloadWithMd5FingerprintAndPresignedUrl() throws Exception {
        MinioProperties properties = new MinioProperties();
        properties.setBucket("gosd");

        byte[] content = "sample-kmz".getBytes(StandardCharsets.UTF_8);
        when(minioClient.getObject(argThat(args ->
                "gosd".equals(args.bucket())
                        && "sample.kmz".equals(args.object())
        ))).thenReturn(new GetObjectResponse(
                Headers.of(),
                "gosd",
                "cn-shenzhen",
                "sample.kmz",
                new ByteArrayInputStream(content)
        ));
        when(minioClient.getPresignedObjectUrl(argThat(args ->
                Http.Method.GET == args.method()
                        && "gosd".equals(args.bucket())
                        && "sample.kmz".equals(args.object())
                        && args.expiry() == 3600
        ))).thenReturn("https://example.com/sample.kmz?X-Amz-Expires=3600");

        WaylineService service = new WaylineService(minioClient, properties);

        WaylineFileResponse response = service.createSampleKmzDownload("event-001");

        assertThat(response.fingerprint()).isEqualTo("aca4fd206d75c05db92f6a97b32d2542");
        assertThat(response.url()).isEqualTo("https://example.com/sample.kmz?X-Amz-Expires=3600");
    }
}
