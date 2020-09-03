import {Component, OnInit} from "@angular/core";
import {Dashboard} from "../../model/dashboard";
import {DashboardService} from "../../service/dashboard.service";
import {MatDialog} from "@angular/material/dialog";
import {DashboardViewComponent} from "../dashboard-view/dashboard-view.component";

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

    new() {
        let dialogRef = this.dialog.open(DashboardViewComponent);
        dialogRef.afterClosed()
            .subscribe(() => this.load());
    }

    edit(dashboard: Dashboard) {
        let dialogRef = this.dialog.open(DashboardViewComponent, {data: dashboard.id});
        dialogRef.afterClosed()
            .subscribe((result) => {
                if (result) {
                    this.load()
                }
            });
    }

}