package com.egotibi.ticketservice.configs.rabbitmq.payloads;

import java.time.Instant;
import java.util.UUID;

import com.egotibi.ticketservice.modules.ticket.helpers.TicketCategory;
import com.egotibi.ticketservice.modules.ticket.helpers.TicketPriority;

public record TicketCreatedEvent(
        UUID ticketId,
        String title,
        TicketCategory category,
        TicketPriority priority,
        String requesterEmail,
        Instant createdAt) {
}
