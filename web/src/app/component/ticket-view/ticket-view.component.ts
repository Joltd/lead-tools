import {Component, Inject, OnInit} from "@angular/core";
import {TicketService} from "../../service/ticket.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Ticket} from "../../model/ticket";
import {Attribute} from "../../model/attribute";
import {TicketAttribute} from "../../model/ticket-attribute";
import {AttributeService} from "../../service/attribute.service";
import {FormControl} from "@angular/forms";
import {EMPTY, Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {Dashboard} from "../../model/dashboard";

@Component({
    selector: 'ticket-view',
    templateUrl: 'ticket-view.component.html',
    styleUrls: ['ticket-view.component.scss']
})
export class TicketViewComponent implements OnInit {

    attributes: Attribute[];
    attributeControl: FormControl = new FormControl();
    filteredAttributes: Observable<Attribute[]> = EMPTY;

    ticket: Ticket;

    constructor(
        private attributeService: AttributeService,
        private ticketService: TicketService,
        public dialogRef: MatDialogRef<TicketViewComponent>,
        @Inject(MAT_DIALOG_DATA) public data: TicketViewData
    ) {}

    ngOnInit(): void {
        this.attributeService.load()
            .subscribe(result => {
                this.attributes = result;
                this.filteredAttributes = this.attributeControl.valueChanges.pipe(startWith(''),map(value => this.filterAttributes(value)))
            });
        this.ticket = this.data.ticket;
        if (!this.ticket.id) {
            this.ticket.addAttributesFromDashboard(this.data.dashboard);
            return;
        }
        this.ticketService.loadById(this.ticket.id)
            .subscribe(result => {
                this.ticket = result;
                this.ticket.addAttributesFromDashboard(this.data.dashboard);
            });
    }

    private filterAttributes(value: string | Attribute): Attribute[] {
        return this.attributes.filter(attribute => {
            if (this.attributeAdded(attribute)) {
                return false;
            }

            if (!value) {
                return true;
            }

            return typeof value == 'string'
                ? attribute.name.toLowerCase().includes(value.toLowerCase())
                : attribute.id == value.id;
        });
    }

    private attributeAdded(attribute: Attribute) {
        return this.ticket.attributes.find(ticketAttribute => ticketAttribute.attribute.id == attribute.id);
    }

    hasAllowedAttributes(): Observable<boolean> {
        return this.filteredAttributes.pipe(map(attributes => attributes.length > 0));
    }

    displayFunction(attribute: Attribute): string {
        return attribute ? attribute.name : '';
    }

    save() {
        this.ticketService.update(this.ticket).subscribe(() => this.dialogRef.close(true));
    }

    cancel() {
        this.dialogRef.close(false);
    }

    delete(attribute: TicketAttribute) {
        this.ticket.attributes = this.ticket.attributes.filter(attr => attr.attribute.id != attribute.attribute.id);
        this.attributeControl.reset();
    }

    add() {
        let ticketAttribute = new TicketAttribute();
        ticketAttribute.attribute = this.attributeControl.value;
        this.ticket.attributes.push(ticketAttribute);
        this.attributeControl.reset();
    }

    deleteTicket() {
        this.ticketService.delete(this.ticket.id)
            .subscribe(() => this.dialogRef.close(true));
    }

}

export class TicketViewData {
    dashboard: Dashboard;
    ticket: Ticket;

    constructor(dashboard: Dashboard, ticket: Ticket) {
        this.dashboard = dashboard;
        this.ticket = ticket;
    }
}