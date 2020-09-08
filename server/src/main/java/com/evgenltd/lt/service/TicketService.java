package com.evgenltd.lt.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Subtask;
import com.atlassian.jira.rest.client.api.domain.User;
import com.evgenltd.lt.entity.Dashboard;
import com.evgenltd.lt.entity.Ticket;
import com.evgenltd.lt.record.JiraTicketRecord;
import com.evgenltd.lt.record.TicketRecord;
import com.evgenltd.lt.repository.DashboardRepository;
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

    private final DashboardRepository dashboardRepository;
    private final TicketRepository ticketRepository;

    private final JiraRestClient client;

    public TicketService(
            final DashboardRepository dashboardRepository,
            final TicketRepository ticketRepository,
            final JiraRestClient client
    ) {
        this.dashboardRepository = dashboardRepository;
        this.ticketRepository = ticketRepository;
        this.client = client;
    }

    public List<Ticket> loadByDashboard(final Long dashboardId) {
        final Dashboard dashboard = dashboardRepository.getOne(dashboardId);
        final String query = dashboard.getQuery();
        return ticketRepository.findAll()
                .stream()
                .filter(ticket -> ticketMatchQuery(ticket, query))
                .collect(Collectors.toList());
    }

    private boolean ticketMatchQuery(final Ticket ticket, final String query) {
        return true;
    }

}
