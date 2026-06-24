package com.egotibi.ticketservice.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {
    private final boolean success = false;
    private int status;
    private String error;
    // private String code;
    private Object details;
    private String path;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
