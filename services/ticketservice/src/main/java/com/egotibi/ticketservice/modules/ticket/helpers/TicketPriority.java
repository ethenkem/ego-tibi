package com.egotibi.ticketservice.modules.ticket.helpers;

import lombok.Getter;

@Getter
public enum TicketPriority {
    LOW(60),
    MEDIUM(30),
    HIGH(15),
    URGENT(5);

    private final int slaInMinutes;

    TicketPriority(int slaInMinutes) {
        this.slaInMinutes = slaInMinutes;
    }

    public int getSlaInMinutes() {
        return this.slaInMinutes;
    }
}
