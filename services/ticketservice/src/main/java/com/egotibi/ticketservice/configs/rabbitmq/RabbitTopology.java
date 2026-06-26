package com.egotibi.ticketservice.configs.rabbitmq;

public class RabbitTopology {
    public static final String EVENT_EXCHANGE = "egotibi.events";

    public static final String ROUTING_KEY_TICKET_CREATED = "ticket.created";
    public static final String ROUTING_KEY_TICKET_ROUTED = "ticket.routed";
    public static final String ROUTING_KEY_TICKET_ESCALATED = "ticket.escalated";
    public static final String ROUTING_KEY_TICKET_ACKNOWLEDGED = "ticket.acknowledged";
    public static final String ROUTING_KEY_TICKET_RESOLVED = "ticket.resolved";

    private RabbitTopology() {
    }

}
