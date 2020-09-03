import {Component, Input, OnInit} from "@angular/core";
import {TicketService} from "../../service/ticket.service";
import {Ticket} from "../../model/ticket";
import {Dashboard} from "../../model/dashboard";
import {MatDialog} from "@angular/material/dialog";
import {TicketViewComponent, TicketViewData} from "../ticket-view/ticket-view.component";
import {TicketAttribute} from "../../model/ticket-attribute";

@Component({
    selector: 'ticket-browser',
    templateUrl: 'ticket-browser.component.html',
    styleUrls: ['ticket-browser.component.scss']
})
export class TicketBrowserComponent implements OnInit {

    @Input()
    dashboard: Dashboard;

    tickets: Ticket[] = [];

    constructor(private ticketService: TicketService, private dialog: MatDialog) {}

    ngOnInit(): void {
        this.load();
    }

    load() {
        this.ticketService.load(this.dashboard).subscribe(result => {
            for (let ticket of result) {
                ticket.addAttributesFromDashboard(this.dashboard);
            }
            this.tickets = result;
        });
    }

    onHeaderChanged() {

    }

    getColumnWidth(ticketAttribute: TicketAttribute): number {
        return this.dashboard.get(ticketAttribute.attribute).getFullWidth();
    }

    edit(ticket: Ticket) {
        let dialogRef = this.dialog.open(TicketViewComponent, {data: new TicketViewData(this.dashboard, ticket)});
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.load();
            }
        });
    }

}