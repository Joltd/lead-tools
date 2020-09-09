package com.evgenltd.lt.component.query;

import com.evgenltd.lt.entity.Ticket;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.stream.Collectors;

public class QueryBuilder {

    private static final Value NOT_FOUND = new Value(null, null);

    public static Query from(final Ticket ticket, final String query) {
        final Map<String, Value> index = ticket.getAttributes()
                .stream()
                .collect(Collectors.toMap(
                        ticketAttribute -> ticketAttribute.getAttribute().getName(),
                        ticketAttribute -> new Value(ticketAttribute.getValue(), ticketAttribute.getAttribute().getType())
                ));
        try (final InputStream stream = new ByteArrayInputStream(query.getBytes())) {
            return new Query(attribute -> index.getOrDefault(attribute, NOT_FOUND), stream);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
