package com.egotibi.ticketservice.modules.ticket.dto;

import com.egotibi.ticketservice.modules.ticket.helpers.TicketCategory;
import com.egotibi.ticketservice.modules.ticket.helpers.TicketPriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTicket {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Requester email is required")
    private String requesterEmail;

    @NotNull(message = "Category is required")
    private TicketCategory category;

    @NotNull(message = "Priority is required")
    private TicketPriority priority;
}
