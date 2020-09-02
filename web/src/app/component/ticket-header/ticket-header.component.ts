import {Component} from "@angular/core";
import {DashboardColumn} from "../../model/dashboard-column";
import {Attribute} from "../../model/attribute";
import {CdkDrag, CdkDragDrop} from "@angular/cdk/drag-drop";

@Component({
    selector: 'ticket-header',
    templateUrl: 'ticket-header.component.html',
    styleUrls: ['ticket-header.component.scss']
})
export class TicketHeaderComponent {

    columns: DashboardColumn[] = [];
    columnReorderDisabled: boolean = false;

    constructor() {
        this.columns = [
            DashboardColumn.from({
                attribute: Attribute.from({id: 1, name: 'Number', type: 'STRING'}),
                order: 'NONE',
                position: 0,
                width: 700
            }),
            DashboardColumn.from({
                attribute: Attribute.from({id: 2, name: 'Title', type: 'STRING'}),
                order: 'NONE',
                position: 1,
                width: 500
            }),
            DashboardColumn.from({
                attribute: Attribute.from({id: 3, name: 'Date', type: 'DATE'}),
                order: 'NONE',
                position: 2,
                width: 700
            }),
            DashboardColumn.from({
                attribute: Attribute.from({id: 4, name: 'Priority', type: 'NUMBER'}),
                order: 'NONE',
                position: 3,
                width: 400
            })
        ];
    }

    getFullWidth() {
        return this.columns
            .map(column => column.getFullWidth())
            .reduce((prev, current) => prev + current);
    }

    isLastColumn(column: DashboardColumn) {
        if (this.columns.length == 0) {
            return true;
        }

        return this.columns[this.columns.length - 1] == column;
    }

    drop(event: CdkDragDrop<DashboardColumn[]>) {
        this.columnReorderDisabled = false;
        if (event.previousIndex == event.currentIndex) {
            return;
        }
        let column = this.columns[event.previousIndex];
        this.columns.splice(event.previousIndex, 1);
        this.columns.splice(event.currentIndex, 0, column);
        for (let index = 0; index < this.columns.length; index++) {
            this.columns[index].position = index;
        }
    }

    order(column: DashboardColumn) {
        if (column.order == 'ASC') {
            column.order = 'DESC';
        } else if (column.order == 'DESC') {
            column.order = 'NONE';
        } else if (column.order == 'NONE') {
            column.order = 'ASC';
        }
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
    }

}