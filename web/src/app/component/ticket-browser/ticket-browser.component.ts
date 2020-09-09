import {Component, Input, OnInit} from "@angular/core";
import {TicketService} from "../../service/ticket.service";
import {Ticket} from "../../model/ticket";
import {Dashboard} from "../../model/dashboard";
import {MatDialog} from "@angular/material/dialog";
import {TicketViewComponent, TicketViewData} from "../ticket-view/ticket-view.component";
import {TicketAttribute} from "../../model/ticket-attribute";
import {TicketBatchComponent} from "../ticket-batch/ticket-batch.component";
import {DashboardService} from "../../service/dashboard.service";
import {AttributeService} from "../../service/attribute.service";
import {EMPTY, Observable} from "rxjs";
import {FormControl} from "@angular/forms";
import {map, pairwise, startWith} from "rxjs/operators";

@Component({
    selector: 'ticket-browser',
    templateUrl: 'ticket-browser.component.html',
    styleUrls: ['ticket-browser.component.scss']
})
export class TicketBrowserComponent implements OnInit {

    // options: string[] = ['&&', '||', '!='];
    // filteredOptions: Observable<string[]> = EMPTY;
    // optionsControl: FormControl = new FormControl();

    @Input()
    dashboard: Dashboard;

    tickets: Ticket[] = [];

    constructor(
        private ticketService: TicketService,
        private dashboardService: DashboardService,
        private attributeService: AttributeService,
        private dialog: MatDialog
    ) {}

    ngOnInit(): void {
        this.load();
    }

    load() {
        // this.attributeService.load()
        //     .subscribe((result) => {
        //         for (let attribute of result) {
        //             this.options.push('[' + attribute.name + ']');
        //         }
        //         this.filteredOptions = this.optionsControl
        //             .valueChanges
        //             .pipe(startWith(''), pairwise(), map(value => this.filterOptions(value)));
        //     })
        this.ticketService.load(this.dashboard).subscribe(result => {
            for (let ticket of result) {
                ticket.addAttributesFromDashboard(this.dashboard);
            }
            this.tickets = result.sort((left, right) => this.compareTickets(left, right));
        });
    }

    onHeaderChanged() {
        this.load();
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

    // private filterOptions(oldNew: string[]): string[] {
    //     let value = this.detectChangedWord(oldNew[0], oldNew[1]);
    //     return this.options.filter(option => {
    //         return option.indexOf(value) >= 0;
    //     })
    // }
    //
    // private detectChangedWord(oldValue: string, newValue: string): string {
    //     let oldWords = oldValue.split(' ');
    //     let newWords = newValue.split(' ');
    //
    //     for (let index = 0; index < oldWords.length || index < newWords.length; index++) {
    //         if (oldWords[index] != newWords[index]) {
    //             return newWords[index];
    //         }
    //     }
    //
    //     if (newWords.length > oldWords.length) {
    //         return newWords[newWords.length - 1];
    //     }
    //
    //     return '';
    // }

    search() {
        this.dashboardService.update(this.dashboard)
            .subscribe(() => this.load());
    }

    jira() {
        this.ticketService.jira(null).subscribe(() => this.load());
    }

    jiraAddBatch() {
        let dialogRef = this.dialog.open(TicketBatchComponent);
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.load();
            }
        })
    }

    private compareTickets(left: Ticket, right: Ticket): number {
        let orderColumn = this.dashboard.columns.find(column => column.order != 'NONE');
        if (!orderColumn) {
            return 0;
        }

        let attribute = orderColumn.attribute.name;
        let leftTicketAttribute = left.attributes.find(ticketAttribute => ticketAttribute.attribute.name == attribute);
        let rightTicketAttribute = right.attributes.find(ticketAttribute => ticketAttribute.attribute.name == attribute);

        let result = this.compare(leftTicketAttribute?.value, rightTicketAttribute?.value);

        if (orderColumn.order == 'ASC') {
            return result;
        } else {
            return -result;
        }

    }

    private compare(left: string, right: string): number {
        if (left) {
            return right ? left.localeCompare(right) : 1;
        } else {
            return right ? -1 : 0;
        }

    }

}