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
        this.updateColumns();
    }

    order(column: DashboardColumn) {
        if (column.order == 'ASC') {
            column.order = 'DESC';
        } else if (column.order == 'DESC') {
            column.order = 'NONE';
        } else if (column.order == 'NONE') {
            column.order = 'ASC';
        }
        this.updateColumnsWithEvent();
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

    private updateColumnsWithEvent() {
        this.dashboardService.update(this.dashboard).subscribe(() => this.headerChanged.emit());
    }
}