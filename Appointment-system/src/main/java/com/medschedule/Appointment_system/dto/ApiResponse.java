package com.medschedule.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * OOP CONCEPT: ENCAPSULATION + ABSTRACTION
 *
 * Generic wrapper class. Encapsulates success flag, message,
 * and data — the caller always gets a consistent shape.
 * Using Generics (T) is OOP abstraction — works for any data type.
 */
@Getter
@Setter
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // Private constructor — use factory methods below (Information Hiding)
    private ApiResponse(boolean success, String message, T data) {
        this.success   = success;
        this.message   = message;
        this.data      = data;
        this.timestamp = LocalDateTime.now();
    }

    // Factory method: success with data
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    // Factory method: success without data
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(true, message, null);
    }

    // Factory method: error
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
