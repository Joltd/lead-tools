package com.evgenltd.lt.component.query;

import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.entity.Ticket;
import com.evgenltd.lt.entity.TicketAttribute;
import com.evgenltd.lt.entity.Type;
import org.junit.Test;

public class QueryTest {

    @Test
    public void test() throws Exception {
        final Ticket ticket = new Ticket();
        ticket.getAttributes().add(newTicketAttribute("Key", Type.STRING, "1234"));
        ticket.getAttributes().add(newTicketAttribute("Title", Type.STRING, "Some"));
        ticket.getAttributes().add(newTicketAttribute("Number", Type.NUMBER, null));


        final boolean result = QueryBuilder.from(ticket, "[Title] ~ 'om.*'").match();
        System.out.println(result);
    }

    private TicketAttribute newTicketAttribute(final String name, final Type type, final String value) {
        final Attribute attribute = new Attribute();
        attribute.setName(name);
        attribute.setType(type);
        final TicketAttribute ticketAttribute = new TicketAttribute();
        ticketAttribute.setAttribute(attribute);
        ticketAttribute.setValue(value);
        return ticketAttribute;
    }

}
