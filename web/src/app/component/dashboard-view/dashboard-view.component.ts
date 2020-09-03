import {Component, Inject, OnInit} from "@angular/core";
import {Dashboard} from "../../model/dashboard";
import {DashboardService} from "../../service/dashboard.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {AttributeService} from "../../service/attribute.service";
import {Attribute} from "../../model/attribute";
import {DashboardColumn} from "../../model/dashboard-column";

@Component({
    selector: 'dashboard-view',
    templateUrl: 'dashboard-view.component.html',
    styleUrls: ['dashboard-view.component.scss']
})
export class DashboardViewComponent implements OnInit {

    allowedColumns: DashboardColumn[] = [];
    dashboard: Dashboard;

    compareFunction = (left: DashboardColumn, right: DashboardColumn) => left.attribute.id == right.attribute.id;

    constructor(
        private attributeService: AttributeService,
        public dashboardService: DashboardService,
        public dialogRef: MatDialogRef<DashboardViewComponent>,
        @Inject(MAT_DIALOG_DATA) public id: number
    ) {}

    ngOnInit(): void {
        this.dashboard = new Dashboard();
        if (!this.id) {
            this.attributeService.load()
                .subscribe(result => {
                    this.allowedColumns = result.map(attribute => DashboardColumn.fromAttribute(attribute));
                });
            return;
        }

        this.dashboardService.loadById(this.id)
            .subscribe(result => {
                this.dashboard = result;
                for (let column of this.dashboard.columns) {
                    this.allowedColumns.push(column);
                }
                this.attributeService.load()
                    .subscribe(result => {
                        for (let attribute of result) {
                            if (!this.hasAttribute(attribute)) {
                                let column = DashboardColumn.fromAttribute(attribute);
                                this.allowedColumns.push(column);
                            }
                        }
                    });

            });
    }

    delete() {
        this.dashboardService.delete(this.dashboard.id)
            .subscribe(() => this.dialogRef.close(true));
    }

    save() {
        this.dashboard.columns.sort((left, right) => left.position - right.position);
        debugger
        for (let index = 0; index < this.dashboard.columns.length; index++){
            let column = this.dashboard.columns[index];
            column.position = index;
        }
        this.dashboardService.update(this.dashboard)
            .subscribe(() => this.dialogRef.close(true));
    }

    cancel() {
        this.dialogRef.close(false);
    }

    private hasAttribute(attribute: Attribute): boolean {
        for (let column of this.dashboard.columns) {
            if (column.attribute.id == attribute.id) {
                return true;
            }
        }
        return false;
    }

}

// export class AttributeSelection {
//     attribute: Attribute;
//     selected: boolean;
//
//     constructor(attribute: Attribute) {
//         this.attribute = attribute;
//     }
// }