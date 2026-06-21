package com.egotibi.ticketservice.modules.ticket;

import java.time.Instant;
import java.util.UUID;

import com.egotibi.ticketservice.modules.ticket.helpers.TicketCategory;
import com.egotibi.ticketservice.modules.ticket.helpers.TicketPriority;
import com.egotibi.ticketservice.modules.ticket.helpers.TicketStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String description;
    private String requesterEmail;

    @Enumerated(EnumType.STRING)
    private TicketCategory category;

    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private String assignedTeam;

    @Column(nullable = false, updatable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(nullable = false, updatable = false)
    private Instant updatedAt;
    private Instant routedAt;
    private Instant acknowledgedAt;
    private Instant resolvedAt;
}
