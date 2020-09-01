package com.evgenltd.lt.record;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record JiraTicketRecord(
        String title,
        String assignee,
        String status
) {
}
