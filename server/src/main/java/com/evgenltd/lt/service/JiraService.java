package com.evgenltd.lt.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.User;
import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.entity.Ticket;
import com.evgenltd.lt.entity.TicketAttribute;
import com.evgenltd.lt.repository.AttributeRepository;
import com.evgenltd.lt.repository.TicketAttributeRepository;
import com.evgenltd.lt.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JiraService {

    private final AttributeRepository attributeRepository;
    private final TicketAttributeRepository ticketAttributeRepository;
    private final JiraRestClient client;

    public JiraService(
            final AttributeRepository attributeRepository,
            final TicketAttributeRepository ticketAttributeRepository,
            final JiraRestClient client
    ) {
        this.attributeRepository = attributeRepository;
        this.ticketAttributeRepository = ticketAttributeRepository;
        this.client = client;
    }

    public void load() {
        final List<TicketAttribute> result = ticketAttributeRepository.findByAttributeName("Jira");
        for (final TicketAttribute ticketAttribute : result) {
            final String number = ticketAttribute.getValue();

            final Issue issue = client.getIssueClient()
                    .getIssue(number)
                    .recover(error -> null)
                    .claim();

            setAttribute(ticketAttribute.getTicket(), "Summary", issue.getSummary());
            setAttribute(ticketAttribute.getTicket(), "Assignee", Optional.ofNullable(issue.getAssignee()).map(User::getDisplayName).orElse(null));
            setAttribute(ticketAttribute.getTicket(), "Status", issue.getStatus().getName());
        }
    }

    private void setAttribute(final Ticket ticket, final String attributeName, final String value) {
        final TicketAttribute ticketAttribute = ticket.getAttributes()
                .stream()
                .filter(attribute -> attribute.getAttribute().getName().equals(attributeName))
                .findFirst()
                .orElseGet(() -> newTicketAttribute(ticket, attributeName));

        if (ticketAttribute == null) {
            return;
        }

        ticketAttribute.setValue(value);
        ticketAttributeRepository.save(ticketAttribute);
    }

    private TicketAttribute newTicketAttribute(final Ticket ticket, final String attributeName) {
        final Attribute attribute = attributeRepository.findByName(attributeName).orElse(null);
        if (attribute == null) {
            return null;
        }

        final TicketAttribute ticketAttribute = new TicketAttribute();
        ticketAttribute.setAttribute(attribute);
        ticketAttribute.setTicket(ticket);
        ticketAttributeRepository.save(ticketAttribute);
        return ticketAttribute;
    }

}
