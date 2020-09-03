import {Component, Input, OnInit} from "@angular/core";
import {TicketService} from "../../service/ticket.service";
import {Ticket} from "../../model/ticket";
import {Dashboard} from "../../model/dashboard";

@Component({
    selector: 'ticket-browser',
    templateUrl: 'ticket-browser.component.html',
    styleUrls: ['ticket-browser.component.scss']
})
export class TicketBrowserComponent implements OnInit {

    @Input()
    dashboard: Dashboard;

    tickets: Ticket[] = [];

    constructor(public ticketService: TicketService) {}

    ngOnInit(): void {
        this.load();
    }

    load() {
        this.ticketService.load(this.dashboard).subscribe(result => this.tickets = result);
    }

    onHeaderChanged() {
        // this.load();
    }

}