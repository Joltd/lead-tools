import {Component, Input, OnInit} from "@angular/core";
import {TicketService} from "../../service/ticket.service";

@Component({
    selector: 'ticket-browser',
    templateUrl: 'ticket-browser.component.html',
    styleUrls: ['ticket-browser.component.scss']
})
export class TicketBrowserComponent implements OnInit {

    constructor(public ticketService: TicketService) {}

    ngOnInit(): void {
        this.ticketService.loadTickets().subscribe();
    }

}