package com.evgenltd.lt.record;

import com.evgenltd.lt.entity.Attribute;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record TicketAttributeRecord(
        Attribute attribute,
        String value
) {}
