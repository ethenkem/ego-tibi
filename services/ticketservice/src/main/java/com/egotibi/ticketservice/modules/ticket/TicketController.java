package com.egotibi.ticketservice.modules.ticket;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egotibi.ticketservice.shared.ApiResponseFactory;
import com.egotibi.ticketservice.shared.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<ApiResponse<Ticket>> createTicket() {
        Ticket ticket = this.ticketService.createTicket(null);
        ApiResponse<Ticket> response = ApiResponseFactory.<Ticket>success(ticket, "Ticket has been created");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
