package com.evgenltd.lt.record;

import com.evgenltd.lt.component.color.ColorResolver;
import com.evgenltd.lt.entity.TicketAttribute;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record TicketAttributeRecord(
        Long id,
        AttributeRecord attribute,
        String value,
        String color
) {

    public static TicketAttributeRecord from(final TicketAttribute ticketAttribute) {
        return new TicketAttributeRecord(
                ticketAttribute.getId(),
                AttributeRecord.fromSimple(ticketAttribute.getAttribute()),
                ticketAttribute.getValue(),
                ColorResolver.resolve(ticketAttribute)
        );
    }

    public TicketAttribute toEntity() {
        final TicketAttribute ticketAttribute = new TicketAttribute();
        ticketAttribute.setId(id());
        ticketAttribute.setAttribute(attribute().toEntity());
        ticketAttribute.setValue(value());
        return ticketAttribute;
    }

}
