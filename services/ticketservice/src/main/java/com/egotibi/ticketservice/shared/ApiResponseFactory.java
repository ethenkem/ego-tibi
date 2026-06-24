package com.egotibi.ticketservice.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.egotibi.ticketservice.shared.dto.ApiErrorResponse;
import com.egotibi.ticketservice.shared.dto.ApiResponse;

public final class ApiResponseFactory {
    private ApiResponseFactory() {
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiErrorResponse error(int status, String path, String error, Object detials) {
        return ApiErrorResponse.builder()
                .status(status)
                .error(error)
                .details(detials)
                .path(path)
                .build();
    }
}
