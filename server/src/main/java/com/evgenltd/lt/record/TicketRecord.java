package com.evgenltd.lt.record;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record TicketRecord(
        Long id,
        String number,
        String title,
        String assignee,
        String status,
        String comment,
        Boolean tracked,
        List<TicketRecord> sub
) {}
