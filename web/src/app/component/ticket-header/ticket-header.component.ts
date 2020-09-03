import {Component, EventEmitter, Input, Output} from "@angular/core";
import {DashboardColumn} from "../../model/dashboard-column";
import {CdkDragDrop} from "@angular/cdk/drag-drop";
import {DashboardService} from "../../service/dashboard.service";
import {Dashboard} from "../../model/dashboard";

@Component({
    selector: 'ticket-header',
    templateUrl: 'ticket-header.component.html',
    styleUrls: ['ticket-header.component.scss']
})
export class TicketHeaderComponent {

    @Input()
    dashboard: Dashboard;

    @Output()
    headerChanged: EventEmitter<void> = new EventEmitter<void>();

    columnReorderDisabled: boolean = false;

    constructor(private dashboardService: DashboardService) {}

    getFullWidth(): number {
        if (!this.dashboard || this.dashboard.columns.length == 0) {
            return 0;
        }
        return this.dashboard.columns
            .map(column => column.getFullWidth())
            .reduce((prev, current) => prev + current);
    }

    isLastColumn(column: DashboardColumn): boolean {
        if (this.dashboard.columns.length == 0) {
            return true;
        }

        return this.dashboard.columns[this.dashboard.columns.length - 1] == column;
    }

    drop(event: CdkDragDrop<DashboardColumn[]>) {
        this.columnReorderDisabled = false;
        if (event.previousIndex == event.currentIndex) {
            return;
        }
        let column = this.dashboard.columns[event.previousIndex];
        this.dashboard.columns.splice(event.previousIndex, 1);
        this.dashboard.columns.splice(event.currentIndex, 0, column);
        for (let index = 0; index < this.dashboard.columns.length; index++) {
            this.dashboard.columns[index].position = index;
        }
        this.dashboardService.update(this.dashboard);
    }

    order(column: DashboardColumn) {
        if (column.order == 'ASC') {
            column.order = 'DESC';
        } else if (column.order == 'DESC') {
            column.order = 'NONE';
        } else if (column.order == 'NONE') {
            column.order = 'ASC';
        }
        this.dashboardService.update(this.dashboard);
    }

    width(event, column: DashboardColumn) {
        let delta = event.distance.x
        if (delta < 0 && column.width + delta <= 100) {
            return;
        }
        column.widthDelta = event.distance.x;
    }

    widthDone(column: DashboardColumn) {
        column.width = column.width + column.widthDelta;
        column.widthDelta = 0;
        this.updateColumns();
    }

    private updateColumns() {
        this.dashboardService.update(this.dashboard).subscribe();
    }
}