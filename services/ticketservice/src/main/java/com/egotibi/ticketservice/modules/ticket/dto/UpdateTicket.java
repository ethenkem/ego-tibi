package com.egotibi.ticketservice.modules.ticket.dto;

import lombok.Data;

@Data
public class UpdateTicket {
    private String title;
    private String description;
    private String requesterEmail;
    private String category;
    private String priority;
    private String status;
}
