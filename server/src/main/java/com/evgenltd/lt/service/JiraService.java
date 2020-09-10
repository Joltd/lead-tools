package com.evgenltd.lt.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.User;
import com.evgenltd.lt.component.Utils;
import com.evgenltd.lt.entity.Attribute;
import com.evgenltd.lt.entity.Ticket;
import com.evgenltd.lt.entity.TicketAttribute;
import com.evgenltd.lt.repository.AttributeRepository;
import com.evgenltd.lt.repository.TicketAttributeRepository;
import com.evgenltd.lt.repository.TicketRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class JiraService {

    private final AttributeRepository attributeRepository;
    private final TicketRepository ticketRepository;
    private final TicketAttributeRepository ticketAttributeRepository;
    private final JiraRestClient client;

    public JiraService(
            final AttributeRepository attributeRepository,
            final TicketRepository ticketRepository,
            final TicketAttributeRepository ticketAttributeRepository,
            final JiraRestClient client
    ) {
        this.attributeRepository = attributeRepository;
        this.ticketRepository = ticketRepository;
        this.ticketAttributeRepository = ticketAttributeRepository;
        this.client = client;
    }

    public void addBatch(final List<String> numbers) {
        for (final String number : numbers) {
            if (Utils.isBlank(number)) {
                continue;
            }

            final Optional<TicketAttribute> result = ticketAttributeRepository.findByAttributeNameAndValue("Jira", number);
            if (result.isEmpty()) {
                final Attribute attribute = attributeRepository.findByName("Jira").orElse(null);
                if (attribute == null) {
                    continue;
                }

                final Ticket ticket = ticketRepository.save(new Ticket());
                final TicketAttribute ticketAttribute = new TicketAttribute();
                ticketAttribute.setAttribute(attribute);
                ticketAttribute.setTicket(ticket);
                ticketAttribute.setValue(number);
                ticketAttributeRepository.save(ticketAttribute);
            }
        }
    }

    @Scheduled(cron = "0 0 10-20 * * *")
    public void scheduled() {
        load();
    }

    @Async
    public void load() {
        final List<TicketAttribute> result = ticketAttributeRepository.findByAttributeName("Jira");
        for (final TicketAttribute ticketAttribute : result) {
            final String number = ticketAttribute.getValue();

            final Issue issue = client.getIssueClient()
                    .getIssue(number)
                    .recover(error -> null)
                    .claim();

            final Ticket ticket = ticketAttribute.getTicket();
            setAttribute(ticket, "Summary", issue.getSummary());
            setAttribute(ticket, "Assignee", Optional.ofNullable(issue.getAssignee()).map(User::getDisplayName).orElse(null));
            setAttribute(ticket, "Status", issue.getStatus().getName());
            setAttribute(ticket, "Story Points", issue.getFields());
            setAttribute(ticket, "Resolve Expected On", issue.getFields());
        }
    }

    private void setAttribute(final Ticket ticket, final String attributeName, final Iterable<IssueField> fields) {
        StreamSupport.stream(fields.spliterator(), false)
                .filter(field -> Objects.equals(field.getName(), attributeName))
                .findFirst()
                .ifPresent(field -> setAttribute(ticket, attributeName, getValue(field)));
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

    private String getValue(final IssueField field) {
        final Object value = field.getValue();
        if (value == null) {
            return null;
        }

        if (value instanceof String str) {
            return str;
        }

        if (value instanceof Double dbl) {
            return dbl.toString();
        }

        return value.toString();
    }

}
