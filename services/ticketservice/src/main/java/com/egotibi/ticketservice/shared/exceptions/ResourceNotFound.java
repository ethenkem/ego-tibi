package com.egotibi.ticketservice.shared.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends GlobalException {
    public ResourceNotFound(String resourceType, String resourceId) {
        super(HttpStatus.NOT_FOUND, resourceType + " with ID " + resourceId + " not found");
    }
}
