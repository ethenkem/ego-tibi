package com.egotibi.ticketservice.config.rabbitmq.payloads;

import java.time.Instant;
import java.util.UUID;

public record TicketResolvedEvent(
                UUID ticketId,
                Instant resolvedAt) {
}
