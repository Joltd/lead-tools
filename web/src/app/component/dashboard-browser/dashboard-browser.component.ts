import {Component, OnInit} from "@angular/core";
import {Dashboard} from "../../model/dashboard";
import {DashboardService} from "../../service/dashboard.service";
import {MatDialog} from "@angular/material/dialog";
import {DashboardViewComponent} from "../dashboard-view/dashboard-view.component";
import {TicketViewComponent, TicketViewData} from "../ticket-view/ticket-view.component";
import {Ticket} from "../../model/ticket";

@Component({
    selector: 'dashboard-browser',
    templateUrl: 'dashboard-browser.component.html',
    styleUrls: ['dashboard-browser.component.scss']
})
export class DashboardBrowserComponent implements OnInit {

    dashboards: Dashboard[] = [];

    constructor(public dashboardService: DashboardService, public dialog: MatDialog) {}

    ngOnInit(): void {
        this.load();
    }

    load() {
        this.dashboardService.load()
            .subscribe(result => this.dashboards = result);
    }

    add() {
        let dialogRef = this.dialog.open(DashboardViewComponent);
        dialogRef.afterClosed()
            .subscribe((result) => {
                if (result) {
                    this.load();
                }
            });
    }

    edit(dashboard: Dashboard) {
        let dialogRef = this.dialog.open(DashboardViewComponent, {data: dashboard.id});
        dialogRef.afterClosed()
            .subscribe((result) => {
                if (result) {
                    this.load();
                }
            });
    }

    addTicket(dashboard: Dashboard) {
        let dialogRef = this.dialog.open(TicketViewComponent, {data: new TicketViewData(dashboard, new Ticket())});
        dialogRef.afterClosed()
            .subscribe(result => {
            if (result) {
                this.load();
            }
        });
    }
}