package com.evgenltd.lt.record;

import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.entity.TicketAttribute;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record TicketAttributeRecord(
        Long id,
        Attribute attribute,
        String value
) {

    public static TicketAttributeRecord from(final TicketAttribute ticketAttribute) {
        return new TicketAttributeRecord(
                ticketAttribute.getId(),
                ticketAttribute.getAttribute(),
                ticketAttribute.getValue()
        );
    }

    public TicketAttribute toEntity() {
        final TicketAttribute ticketAttribute = new TicketAttribute();
        ticketAttribute.setId(id());
        ticketAttribute.setAttribute(attribute());
        ticketAttribute.setValue(value());
        return ticketAttribute;
    }

}
