package com.evgenltd.lt.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Subtask;
import com.atlassian.jira.rest.client.api.domain.User;
import com.evgenltd.lt.entity.Ticket;
import com.evgenltd.lt.record.JiraTicketRecord;
import com.evgenltd.lt.record.TicketRecord;
import com.evgenltd.lt.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final JiraRestClient client;

    public TicketService(
            final TicketRepository ticketRepository,
            final JiraRestClient client
    ) {
        this.ticketRepository = ticketRepository;
        this.client = client;
    }

    public JiraTicketRecord loadJiraTicket(final String number) {
        final Issue issue = client.getIssueClient().getIssue(number).claim();
        return toJiraTicketRecord(issue);
    }

    public List<TicketRecord> loadTrackedTickets() {
        return ticketRepository.findByTrackedIsTrue()
                .stream()
                .map(this::ticketToRecord)
                .collect(Collectors.toList());
    }

    public void trackTicket(final String number) {
        final Issue issue = client.getIssueClient()
                .getIssue(number)
                .recover(error -> null)
                .claim();

        if (issue != null) {
            final Ticket ticket = new Ticket();
            ticket.setNumber(number);
            ticket.setTracked(true);
            ticketRepository.save(ticket);
        }
    }

    public void updateComment(final Long id, final String number, final String comment) {
        final Ticket ticket = Optional.ofNullable(id)
                .flatMap(ticketRepository::findById)
                .orElseGet(() -> ticketRepository.findByNumber(number).orElseGet(Ticket::new));

        ticket.setNumber(number);
        ticket.setComment(comment);
        ticketRepository.save(ticket);
    }

    private TicketRecord ticketToRecord(final String number) {
        final Ticket foundTicket = ticketRepository.findByNumber(number).orElseGet(() -> {
            final Ticket ticket = new Ticket();
            ticket.setNumber(number);
            return ticket;
        });
        return ticketToRecord(foundTicket);
    }

    private TicketRecord ticketToRecord(final Ticket ticket) {
        return new TicketRecord(
                ticket.getId(),
                ticket.getNumber(),
                ticket.getComment(),
                ticket.getTracked(),
                new ArrayList<>()
        );
    }

    private JiraTicketRecord toJiraTicketRecord(final Issue issue) {
        return new JiraTicketRecord(
                issue.getSummary(),
                Optional.ofNullable(issue.getAssignee())
                        .map(User::getDisplayName)
                        .orElse(null),
                issue.getStatus().getName()
        );
    }

    private List<TicketRecord> subtaskListToTicketRecordList(final Iterable<Subtask> subtasks) {
        if (subtasks == null) {
            return Collections.emptyList();
        }
        return StreamSupport.stream(subtasks.spliterator(), false)
                .map(Subtask::getIssueKey)
                .map(this::ticketToRecord)
                .collect(Collectors.toList());
    }

}
