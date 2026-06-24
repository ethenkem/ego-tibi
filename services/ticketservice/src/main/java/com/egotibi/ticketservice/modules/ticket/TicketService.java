package com.egotibi.ticketservice.modules.ticket;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.egotibi.ticketservice.modules.ticket.dto.CreateTicket;
import com.egotibi.ticketservice.modules.ticket.dto.TicketResponse;
import com.egotibi.ticketservice.modules.ticket.dto.UpdateTicket;
import com.egotibi.ticketservice.shared.exceptions.ResourceNotFound;

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

    public TicketResponse updateTicket(String ticketId, UpdateTicket dto) {
        Ticket ticket = this.ticketRepository.findById(UUID.fromString(ticketId))
                .orElseThrow(() -> new ResourceNotFound("Ticket", ticketId));
        this.ticketMapper.updateTicketFromDto(dto, ticket);
        ticketRepository.save(ticket);
        return this.ticketMapper.toResponse(ticket);
    }

    public void deleteTicket(String ticketId) {
        Ticket ticket = this.ticketRepository.findById(UUID.fromString(ticketId))
                .orElseThrow(() -> new ResourceNotFound("Ticket", ticketId));
        this.ticketRepository.delete(ticket);
    }
}
