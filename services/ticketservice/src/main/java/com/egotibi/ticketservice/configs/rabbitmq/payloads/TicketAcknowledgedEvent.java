package com.egotibi.ticketservice.configs.rabbitmq.payloads;

import java.time.Instant;
import java.util.UUID;

public record TicketAcknowledgedEvent(
        UUID ticketId,
        Instant acknowledgedAt) {
}