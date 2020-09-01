package com.evgenltd.lt.controller;

import com.evgenltd.lt.record.JiraTicketRecord;
import com.evgenltd.lt.record.TicketRecord;
import com.evgenltd.lt.repository.TicketRepository;
import com.evgenltd.lt.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketRepository ticketRepository;
    private final TicketService ticketService;

    public TicketController(
            final TicketRepository ticketRepository,
            final TicketService ticketService
    ) {
        this.ticketRepository = ticketRepository;
        this.ticketService = ticketService;
    }

    @GetMapping
    public Response<List<TicketRecord>> list() {
        return new Response<>(ticketService.loadTrackedTickets());
    }

    @PostMapping("/comment")
    public Response<Void> updateComment(@RequestBody final TicketRecord ticketRecord) {
        ticketService.updateComment(ticketRecord.id(), ticketRecord.number(), ticketRecord.comment());
        return new Response<>();
    }

    @PostMapping("/track/{number}")
    public Response<Void> trackTicket(@PathVariable("number") final String number) {
        ticketService.trackTicket(number);
        return new Response<>();
    }

    @DeleteMapping("/{id}")
    public Response<Void> remove(@PathVariable("id") final Long id) {
        ticketRepository.deleteById(id);
        return new Response<>();
    }

    @GetMapping("/jira/{number}")
    public Response<JiraTicketRecord> jira(@PathVariable("number") final String number) {
        return new Response<>(ticketService.loadJiraTicket(number));
    }

}
