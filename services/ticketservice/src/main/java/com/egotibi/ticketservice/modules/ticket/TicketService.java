package com.egotibi.ticketservice.modules.ticket;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.egotibi.ticketservice.modules.ticket.dto.CreateTicket;
import com.egotibi.ticketservice.modules.ticket.dto.TicketResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    public TicketResponse createTicket(CreateTicket dto) {
        Ticket ticket = ticketMapper.toEntity(dto);
        ticketRepository.save(ticket);
        return ticketMapper.toResponse(ticket);
    }

    public List<TicketResponse> getAllTickets() {
        List<Ticket> tickets = this.ticketRepository.findAll();
        return tickets.stream().map(ticketMapper::toResponse).collect(Collectors.toList());
    }

}
