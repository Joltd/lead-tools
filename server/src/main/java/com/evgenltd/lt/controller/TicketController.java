package com.evgenltd.lt.controller;

import com.evgenltd.lt.entity.Ticket;
import com.evgenltd.lt.record.TicketRecord;
import com.evgenltd.lt.repository.TicketRepository;
import com.evgenltd.lt.service.JiraService;
import com.evgenltd.lt.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final TicketService ticketService;
    private final JiraService jiraService;

    public TicketController(
            final TicketRepository ticketRepository,
            final TicketService ticketService,
            final JiraService jiraService
    ) {
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
        this.jiraService = jiraService;
    }

    @GetMapping
    public Response<List<TicketRecord>> list(@RequestParam("dashboard") final Long dashboardId) {
        final List<TicketRecord> result = ticketService.loadByDashboard(dashboardId)
                .stream()
                .map(TicketRecord::from)
                .collect(Collectors.toList());
        return new Response<>(result);
    }

    @GetMapping("/{id}")
    public Response<TicketRecord> loadById(@PathVariable("id") final Long id) {
        return new Response<>(TicketRecord.from(ticketRepository.getOne(id)));
    }

    @PostMapping
    public Response<TicketRecord> update(@RequestBody final TicketRecord ticketRecord) {
        final Ticket ticket = ticketRecord.toEntity();
        final Ticket result = ticketRepository.save(ticket);
        return new Response<>(TicketRecord.from(result));
    }

    @DeleteMapping("/{id}")
    public Response<Void> remove(@PathVariable("id") final Long id) {
        ticketRepository.deleteById(id);
        return new Response<>();
    }

    @PostMapping("/jira")
    public Response<Void> jira() {
        jiraService.load();
        return new Response<>();
    }

}
