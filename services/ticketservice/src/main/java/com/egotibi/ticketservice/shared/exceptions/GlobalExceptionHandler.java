package com.egotibi.ticketservice.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import com.egotibi.ticketservice.shared.ApiResponseFactory;
import com.egotibi.ticketservice.shared.dto.ApiErrorResponse;
import com.egotibi.ticketservice.shared.dto.FieldError;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        @ExceptionHandler(GlobalException.class)
        public ResponseEntity<ApiErrorResponse> handleAppException(GlobalException ex, HttpServletRequest req) {
                log.error("GlobalException: {}", ex.getMessage());
                ApiErrorResponse responseBody = ApiResponseFactory.error(ex.getStatusCode(), req.getRequestURI(),
                                ex.getMessage(), null);
                return ResponseEntity.status(ex.getStatusCode().value()).body(responseBody);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
                        MethodArgumentNotValidException ex, HttpServletRequest request) {
                List<FieldError> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                                .toList();

                ApiErrorResponse responseBody = ApiResponseFactory.error(HttpStatus.BAD_REQUEST,
                                request.getRequestURI(),
                                "Validation failed", errors);
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(responseBody);

        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiErrorResponse> handleMalformedJson(
                        HttpMessageNotReadableException ex, HttpServletRequest request) {

                log.error("Malformed JSON request at {}: {}", request.getRequestURI(), ex.getMessage());

                ApiErrorResponse error = ApiErrorResponse.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error("Malformed JSON request. Please check your request body.")
                                .path(request.getRequestURI())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleInternalServerError(Exception ex, HttpServletRequest req) {
                log.error("InternalServerError: {}", ex.getMessage());
                ApiErrorResponse responseBody = ApiResponseFactory.error(HttpStatus.INTERNAL_SERVER_ERROR,
                                req.getRequestURI(), "Internal server error", null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
}
