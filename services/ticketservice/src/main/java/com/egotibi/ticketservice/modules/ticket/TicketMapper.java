package com.egotibi.ticketservice.modules.ticket;

import org.mapstruct.Mapper;

import com.egotibi.ticketservice.modules.ticket.dto.CreateTicket;
import com.egotibi.ticketservice.modules.ticket.dto.TicketReponse;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    Ticket toEntity(CreateTicket dto);

    TicketReponse toResponse(Ticket entity);
}
