import {Component} from "@angular/core";
import {MatDialogRef} from "@angular/material/dialog";
import {TicketService} from "../../service/ticket.service";

@Component({
    selector: 'ticket-batch',
    templateUrl: 'ticket-batch.component.html',
    styleUrls: ['ticket-batch.component.scss']
})
export class TicketBatchComponent {

    numbers: string;

    constructor(
        private ticketService: TicketService,
        public dialogRef: MatDialogRef<TicketBatchComponent>
    ) {}

    addBatch() {
        if (!this.numbers) {
            this.cancel();
        }

        let numbers = this.numbers.split('\n');

        this.ticketService.jira(numbers)
            .subscribe(() => this.dialogRef.close(true));
    }

    cancel() {
        this.dialogRef.close(false);
    }

}