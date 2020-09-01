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
    current: Dashboard;

    editName: string;

    constructor(private dashboardService: DashboardService) {}

    ngOnInit(): void {
        this.load();
    }

    load() {
        this.dashboardService.load().subscribe(result => {
            this.dashboards = result;
            if (result.length > 0 && !this.current) {
                this.current = result[0];
            }
        });
    }

    new() {
        let dashboard = new Dashboard();
        dashboard.name = 'New Dashboard';
        this.dashboardService.update(dashboard)
            .subscribe((result) => {
                this.current = result;
                this.load();
            })
    }

    open(dashboard: Dashboard) {
        this.current = dashboard;
        // this.ticketService.dashboard = dashboard;
        // this.ticketService.loadTickets().subscribe();
    }

    isCurrent(dashboard: Dashboard) {
        return this.current && this.current.id == dashboard.id;
    }

    getDashboardClass(dashboard: Dashboard) {
        return this.isCurrent(dashboard) ? 'btn-primary' : 'btn-light';
    }

    startEditName() {
        this.editName = this.current.name;
    }

    doneEditName() {
        this.current.name = this.editName;
        this.dashboardService.update(this.current).subscribe(() => {
            this.load();
            this.cancelEdiName();
        })
    }

    cancelEdiName() {
        this.editName = null;
    }

    delete() {
        this.dashboardService.delete(this.current.id)
            .subscribe(() => {
                this.current = null;
                this.load();
            })
    }

    search() {

    }
}