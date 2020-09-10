package com.evgenltd.lt.service;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.evgenltd.lt.component.Utils;
import com.evgenltd.lt.component.query.ParseException;
import com.evgenltd.lt.component.query.QueryBuilder;
import com.evgenltd.lt.entity.Dashboard;
import com.evgenltd.lt.entity.Ticket;
import com.evgenltd.lt.repository.DashboardRepository;
import com.evgenltd.lt.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketService.class);

    private final DashboardRepository dashboardRepository;
    private final TicketRepository ticketRepository;

    public TicketService(
            final DashboardRepository dashboardRepository,
            final TicketRepository ticketRepository
    ) {
        this.dashboardRepository = dashboardRepository;
        this.ticketRepository = ticketRepository;
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
        try {
            if (Utils.isBlank(query)) {
                return true;
            }
            return QueryBuilder.from(ticket, query).match();
        } catch (ParseException e) {
            log.error("Query failed", e);
            return false;
        }
    }

}
