package com.egotibi.ticketservice.modules.ticket;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.egotibi.ticketservice.modules.ticket.dto.CreateTicket;
import com.egotibi.ticketservice.modules.ticket.dto.TicketResponse;
import com.egotibi.ticketservice.modules.ticket.dto.UpdateTicket;

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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "routedAt", ignore = true)
    @Mapping(target = "acknowledgedAt", ignore = true)
    @Mapping(target = "resolvedAt", ignore = true)
    void updateTicketFromDto(UpdateTicket dto, @MappingTarget Ticket existingTicket);
}
