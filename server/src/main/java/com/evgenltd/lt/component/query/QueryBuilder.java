package com.evgenltd.lt.component.query;

import com.evgenltd.lt.entity.Ticket;
import com.evgenltd.lt.entity.Type;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.function.Function;
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
        return QueryBuilder.from(query, attribute -> index.getOrDefault(attribute, NOT_FOUND));
    }

    public static Query from(final String query, final String value, final Type type) {
        return QueryBuilder.from(query, attribute -> new Value(value, type));
    }

    private static Query from(final String query, final Function<String, Value> resolveAttribute) {
        try (final InputStream stream = new ByteArrayInputStream(query.getBytes())) {
            return new Query(resolveAttribute, stream);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
