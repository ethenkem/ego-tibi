package com.egotibi.ticketservice.modules.ticket.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketResponse {
    private UUID id;
    private String title;
    private String description;
    private String requesterEmail;
    private String category;
    private String priority;
}
