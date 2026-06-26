package com.egotibi.ticketservice.configs.rabbitmq.payloads;

import java.time.Instant;
import java.util.UUID;

public record TicketResolvedEvent(
        UUID ticketId,
        Instant resolvedAt) {
}
