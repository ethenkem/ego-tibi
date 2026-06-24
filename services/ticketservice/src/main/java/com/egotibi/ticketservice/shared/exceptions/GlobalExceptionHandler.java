package com.egotibi.ticketservice.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
                ApiErrorResponse responseBody = ApiResponseFactory.error(ex.getStatusCode().value(),
                                req.getRequestURI(),
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

                ApiErrorResponse responseBody = ApiResponseFactory.error(HttpStatus.BAD_REQUEST.value(),
                                request.getRequestURI(),
                                "Validation failed", errors);
                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(responseBody);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ApiErrorResponse> handleMalformedJson(HttpMessageNotReadableException ex,
                        HttpServletRequest request) {
                String message = "Malformed JSON request. Please check your request body.";
                ApiErrorResponse error = ApiResponseFactory.error(HttpStatus.BAD_REQUEST.value(),
                                request.getRequestURI(),
                                message, null);
                return ResponseEntity.badRequest().body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleInternalServerError(Exception ex, HttpServletRequest req) {
                if (ex instanceof ErrorResponse errorResponse) {
                        HttpStatusCode status = errorResponse.getStatusCode();
                        log.error("Spring web exception [{}] at {}: {}", status, req.getRequestURI(), ex.getMessage());
                        String error = errorResponse.getBody() != null
                                        && errorResponse.getBody().getDetail() != null
                                                        ? errorResponse.getBody().getDetail()
                                                        : ex.getMessage();
                        ApiErrorResponse responseBody = ApiResponseFactory.error(status.value(), req.getRequestURI(),
                                        error,
                                        null);

                        return ResponseEntity.status(status).body(responseBody);
                }
                log.error("InternalServerError: {}", ex.getMessage());
                ApiErrorResponse responseBody = ApiResponseFactory.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                req.getRequestURI(), "Internal server error", null);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
}
