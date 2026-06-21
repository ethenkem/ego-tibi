package com.egotibi.ticketservice.modules.ticket.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketResponse {
    private String id;
    private String title;
    private String description;
    private String requesterEmail;
    private String category;
    private String priority;
}
