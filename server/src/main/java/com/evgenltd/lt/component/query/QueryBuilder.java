package com.evgenltd.lt.component.query;

import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.entity.Ticket;
import com.evgenltd.lt.entity.TicketAttribute;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QueryBuilder {

    public static Query from(final Ticket ticket, final String query) {
        final Map<String, Attribute.Type> typeIndex = new HashMap<>();
        final Map<String, String> attributeIndex = new HashMap<>();
        for (final TicketAttribute ticketAttribute : ticket.getAttributes()) {
            final Attribute attribute = ticketAttribute.getAttribute();
            typeIndex.put(attribute.getName(), attribute.getType());
            attributeIndex.put(attribute.getName(), ticketAttribute.getValue());
        }
        try (final InputStream stream = new ByteArrayInputStream(query.getBytes())) {
            return new Query(stream, typeIndex, attributeIndex);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
