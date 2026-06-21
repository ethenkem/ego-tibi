package com.egotibi.ticketservice.modules.ticket;

import java.time.Instant;

import com.egotibi.ticketservice.modules.helpers.TicketCategory;
import com.egotibi.ticketservice.modules.helpers.TicketPriority;
import com.egotibi.ticketservice.modules.helpers.TicketStatus;

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
    private String id;

    private String title;
    private String description;
    private String requesterEmail;

    @Enumerated(EnumType.STRING)
    private TicketCategory category;

    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false, updatable = false)
    private Instant updatedAt;
    private Instant routedAt;
    private Instant acknowledgedAt;
    private Instant resolvedAt;
}
