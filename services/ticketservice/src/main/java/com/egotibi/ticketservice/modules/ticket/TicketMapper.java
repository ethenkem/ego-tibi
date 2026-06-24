package com.egotibi.ticketservice.modules.ticket;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.egotibi.ticketservice.modules.ticket.dto.CreateTicket;
import com.egotibi.ticketservice.modules.ticket.dto.TicketResponse;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", constant = "NEW")
    @Mapping(target = "assignedTeam", ignore = true)
    @Mapping(target = "routedAt", ignore = true)
    @Mapping(target = "acknowledgedAt", ignore = true)
    @Mapping(target = "resolvedAt", ignore = true)
    Ticket toEntity(CreateTicket dto);

    TicketResponse toResponse(Ticket entity);
}
