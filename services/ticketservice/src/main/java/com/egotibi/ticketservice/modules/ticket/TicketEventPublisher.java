package com.egotibi.ticketservice.modules.ticket;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.egotibi.ticketservice.configs.rabbitmq.RabbitTopology;
import com.egotibi.ticketservice.configs.rabbitmq.payloads.TicketAcknowledgedEvent;
import com.egotibi.ticketservice.configs.rabbitmq.payloads.TicketCreatedEvent;
import com.egotibi.ticketservice.configs.rabbitmq.payloads.TicketResolvedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TicketEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publishTicketCreated(TicketCreatedEvent event) {
        rabbitTemplate.convertAndSend(RabbitTopology.EVENT_EXCHANGE, RabbitTopology.ROUTING_KEY_TICKET_CREATED, event);
    }

    public void publishTicketAcknowledged(TicketAcknowledgedEvent event) {
        rabbitTemplate.convertAndSend(RabbitTopology.EVENT_EXCHANGE, RabbitTopology.ROUTING_KEY_TICKET_ACKNOWLEDGED,
                event);
    }

    public void publishTicketResolved(TicketResolvedEvent event) {
        rabbitTemplate.convertAndSend(RabbitTopology.EVENT_EXCHANGE,
                RabbitTopology.ROUTING_KEY_TICKET_RESOLVED, event);
    }
}
