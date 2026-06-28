package com.egotibi.ticketservice.modules.ticket.dto;

import com.egotibi.ticketservice.modules.ticket.helpers.TicketCategory;
import com.egotibi.ticketservice.modules.ticket.helpers.TicketPriority;
import com.egotibi.ticketservice.modules.ticket.helpers.TicketStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTicket {

    @Size(min = 1, max = 50)
    @NotBlank(message = "Title is required")
    private String title;
    @Size(min = 1, max = 500)
    @NotBlank(message = "Description is required")
    private String description;
    @Email
    @NotBlank(message = "Requester email is required")
    private String requesterEmail;
    @NotNull(message = "Category is required")
    private TicketCategory category;
    @NotNull(message = "Priority is required")
    private TicketPriority priority;
    @NotNull(message = "Status is required")
    private TicketStatus status;
    private String assignedTeam;
}
