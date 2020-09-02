import {Component, OnInit} from "@angular/core";
import {Dashboard} from "../../model/dashboard";
import {DashboardService} from "../../service/dashboard.service";

@Component({
    selector: 'dashboard-browser',
    templateUrl: 'dashboard-browser.component.html',
    styleUrls: ['dashboard-browser.component.scss']
})
export class DashboardBrowserComponent implements OnInit {

    editName: string;

    constructor(public dashboardService: DashboardService) {}

    ngOnInit(): void {
        this.load();
    }

    load() {
        this.dashboardService.load().subscribe();
    }

    new() {
        let dashboard = new Dashboard();
        dashboard.name = 'New Dashboard';
        this.dashboardService.update(dashboard)
            .subscribe((result) => {
                this.dashboardService.current = result;
                this.load();
            })
    }

    open(dashboard: Dashboard) {
        this.dashboardService.current = dashboard;
    }

    isCurrent(dashboard: Dashboard) {
        return this.dashboardService.current && this.dashboardService.current.id == dashboard.id;
    }

    getDashboardClass(dashboard: Dashboard) {
        return this.isCurrent(dashboard) ? 'btn-primary' : 'btn-light';
    }

    startEditName() {
        this.editName = this.dashboardService.current.name;
    }

    doneEditName() {
        this.dashboardService.current.name = this.editName;
        this.dashboardService.update(this.dashboardService.current).subscribe(() => {
            this.load();
            this.cancelEdiName();
        })
    }

    cancelEdiName() {
        this.editName = null;
    }

    delete() {
        this.dashboardService.delete(this.dashboardService.current.id)
            .subscribe(() => {
                this.dashboardService.current = null;
                this.load();
            })
    }

    search() {

    }

}