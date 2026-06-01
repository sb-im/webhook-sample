package com.example.webhooksample.common;

import io.minio.errors.MinioException;
import jakarta.validation.ConstraintViolationException;
import java.security.ProviderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<ApiResponse<Void>> handleResponseStatus(ResponseStatusException exception) {
        return error(exception.getStatusCode(), exception.getReason());
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class
    })
    ResponseEntity<ApiResponse<Void>> handleBadRequest(Exception exception) {
        return error(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MinioException.class)
    ResponseEntity<ApiResponse<Void>> handleMinio(MinioException exception) {
        return error(HttpStatus.BAD_GATEWAY, "MinIO request failed: " + exception.getMessage());
    }

    @ExceptionHandler(ProviderException.class)
    ResponseEntity<ApiResponse<Void>> handleProvider(ProviderException exception) {
        return error(HttpStatus.BAD_GATEWAY, "MinIO STS request failed: " + exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception exception) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private ResponseEntity<ApiResponse<Void>> error(HttpStatusCode status, String message) {
        return ResponseEntity.status(status).body(ApiResponse.failure(status, message));
    }
}
