import {Component, OnInit} from "@angular/core";
import {Dashboard} from "../../model/dashboard";
import {DashboardService} from "../../service/dashboard.service";
import {TicketService} from "../../service/ticket.service";

@Component({
    selector: 'dashboard-browser',
    templateUrl: 'dashboard-browser.component.html',
    styleUrls: ['dashboard-browser.component.scss']
})
export class DashboardBrowserComponent implements OnInit {

    dashboards: Dashboard[] = [];

    constructor(private dashboardService: DashboardService, private ticketService: TicketService) {}

    ngOnInit(): void {
        this.load();
    }

    load() {
        this.dashboardService.load().subscribe(result => this.dashboards = result);
    }

    open(dashboard: Dashboard) {
        this.ticketService.dashboard = dashboard;
        this.ticketService.loadTickets().subscribe();
    }

}