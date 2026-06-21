package com.egotibi.ticketservice.modules.ticket;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egotibi.ticketservice.modules.ticket.dto.CreateTicket;
import com.egotibi.ticketservice.modules.ticket.dto.TicketResponse;
import com.egotibi.ticketservice.shared.ApiResponseFactory;
import com.egotibi.ticketservice.shared.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<ApiResponse<TicketResponse>> createTicket(@Valid @RequestBody CreateTicket dto) {
        TicketResponse ticketResponse = this.ticketService.createTicket(dto);
        ApiResponse<TicketResponse> response = ApiResponseFactory.<TicketResponse>success(ticketResponse,
                "Ticket has been created");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TicketResponse>>> getAllTickets() {
        List<TicketResponse> ticketResponses = this.ticketService.getAllTickets();
        ApiResponse<List<TicketResponse>> response = ApiResponseFactory.<List<TicketResponse>>success(ticketResponses,
                "Tickets has been fetched");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
