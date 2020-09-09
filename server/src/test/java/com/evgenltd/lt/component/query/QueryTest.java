package com.evgenltd.lt.component.query;

import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.entity.Ticket;
import com.evgenltd.lt.entity.TicketAttribute;
import org.junit.Test;

public class QueryTest {

    @Test
    public void test() throws Exception {
        final Ticket ticket = new Ticket();
        ticket.getAttributes().add(newTicketAttribute("Key", Attribute.Type.STRING, "1234"));
        ticket.getAttributes().add(newTicketAttribute("Title", Attribute.Type.STRING, "Some summary"));

        final boolean result = QueryBuilder.from(ticket, "Key1 = '11234'").match();
        System.out.println(result);
    }

    private TicketAttribute newTicketAttribute(final String name, final Attribute.Type type, final String value) {
        final Attribute attribute = new Attribute();
        attribute.setName(name);
        attribute.setType(type);
        final TicketAttribute ticketAttribute = new TicketAttribute();
        ticketAttribute.setAttribute(attribute);
        ticketAttribute.setValue(value);
        return ticketAttribute;
    }

}
