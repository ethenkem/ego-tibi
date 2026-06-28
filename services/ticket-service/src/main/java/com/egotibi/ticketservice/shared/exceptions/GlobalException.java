package com.egotibi.ticketservice.shared.exceptions;

import org.springframework.http.HttpStatus;

public class GlobalException extends RuntimeException {
    private HttpStatus statusCode;

    public GlobalException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }
}
