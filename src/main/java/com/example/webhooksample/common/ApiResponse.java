package com.example.webhooksample.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public record ApiResponse<T>(
        int code,
        T data,
        String message
) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(0, data, "success");
    }

    public static <T> ApiResponse<T> success(HttpStatusCode status, T data) {
        return success(data);
    }

    public static <T> ApiResponse<T> failure(HttpStatusCode status, String message) {
        return of(status, null, message);
    }

    public static <T> ApiResponse<T> of(HttpStatusCode status, T data, String message) {
        return new ApiResponse<>(status.value(), data, message);
    }
}
