package com.evgenltd.lt.record;

import com.evgenltd.lt.entity.Ticket;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record TicketRecord(
        Long id,
        List<TicketAttributeRecord> attributes
) {

    public static TicketRecord from(final Ticket ticket) {
        return new TicketRecord(
                ticket.getId(),
                ticket.getAttributes()
                        .stream()
                        .map(TicketAttributeRecord::from)
                        .sorted(Comparator.comparing(o -> o.attribute().name()))
                        .collect(Collectors.toList())
        );
    }

    public Ticket toEntity() {
        final Ticket ticket = new Ticket();
        ticket.setId(id());
        attributes().stream()
                .map(TicketAttributeRecord::toEntity)
                .peek(ticketAttribute -> ticketAttribute.setTicket(ticket))
                .forEach(ticketAttribute -> ticket.getAttributes().add(ticketAttribute));
        return ticket;
    }

}
