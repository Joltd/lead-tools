import {Component, OnInit} from "@angular/core";
import {TicketService} from "../../service/ticket.service";
import {Attribute} from "../../model/attribute";
import {Ticket} from "../../model/ticket";

@Component({
    selector: 'ticket-browser',
    templateUrl: 'ticket-browser.component.html',
    styleUrls: ['ticket-browser.component.scss']
})
export class TicketBrowserComponent implements OnInit {

    constructor(public ticketService: TicketService) {}

    ngOnInit(): void {
        this.ticketService.load().subscribe();
    }

    isCurrent(ticket: Ticket) {
        return this.ticketService.current && this.ticketService.current.id == ticket.id;
    }

}