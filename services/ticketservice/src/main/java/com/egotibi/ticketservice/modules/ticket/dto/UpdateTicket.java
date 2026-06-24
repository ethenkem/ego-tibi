package com.egotibi.ticketservice.modules.ticket.dto;

import com.egotibi.ticketservice.modules.ticket.helpers.TicketCategory;
import com.egotibi.ticketservice.modules.ticket.helpers.TicketPriority;
import com.egotibi.ticketservice.modules.ticket.helpers.TicketStatus;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTicket {

    @Size(min = 1, max = 50)
    private String title;
    @Size(min = 1, max = 500)
    private String description;
    @Email
    private String requesterEmail;
    private TicketCategory category;
    private TicketPriority priority;
    private TicketStatus status;
    private String assignedTeam;
}
