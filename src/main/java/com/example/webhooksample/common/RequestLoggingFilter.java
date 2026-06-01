package com.example.webhooksample.common;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final int REQUEST_BODY_CACHE_LIMIT = 1024 * 1024;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = wrap(request);
        ContentCachingResponseWrapper wrappedResponse = wrap(response);
        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            logRequest(wrappedRequest);
            logResponse(wrappedResponse);
            wrappedResponse.copyBodyToResponse();
        }
    }

    private ContentCachingRequestWrapper wrap(HttpServletRequest request) {
        if (request instanceof ContentCachingRequestWrapper wrappedRequest) {
            return wrappedRequest;
        }
        return new ContentCachingRequestWrapper(request, REQUEST_BODY_CACHE_LIMIT);
    }

    private ContentCachingResponseWrapper wrap(HttpServletResponse response) {
        if (response instanceof ContentCachingResponseWrapper wrappedResponse) {
            return wrappedResponse;
        }
        return new ContentCachingResponseWrapper(response);
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        LOGGER.debug(
                "Incoming request method={} path={} headers={} body={}",
                request.getMethod(),
                request.getRequestURI(),
                headers(request),
                body(request)
        );
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        LOGGER.debug(
                "Outgoing response status={} headers={} body={}",
                response.getStatus(),
                headers(response),
                body(response)
        );
    }

    private Map<String, List<String>> headers(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames()).stream()
                .collect(Collectors.toMap(
                        header -> header,
                        header -> Collections.list(request.getHeaders(header))
                ));
    }

    private Map<String, List<String>> headers(HttpServletResponse response) {
        return response.getHeaderNames().stream()
                .collect(Collectors.toMap(
                        header -> header,
                        header -> List.copyOf(response.getHeaders(header))
                ));
    }

    private String body(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        if (content.length == 0) {
            return "";
        }
        return new String(content, charset(request));
    }

    private String body(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        if (content.length == 0) {
            return "";
        }
        return new String(content, charset(response));
    }

    private Charset charset(HttpServletRequest request) {
        String encoding = request.getCharacterEncoding();
        return encoding != null ? Charset.forName(encoding) : StandardCharsets.UTF_8;
    }

    private Charset charset(HttpServletResponse response) {
        String encoding = response.getCharacterEncoding();
        return encoding != null ? Charset.forName(encoding) : StandardCharsets.UTF_8;
    }
}
