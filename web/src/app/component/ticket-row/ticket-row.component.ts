import {Component, Input} from "@angular/core";
import {Ticket} from "../../model/ticket";
import {TicketService} from "../../service/ticket.service";

@Component({
    selector: 'ticket-row',
    templateUrl: 'ticket-row.component.html',
    styleUrls: ['ticket-row.component.scss']
})
export class TicketRowComponent {

    @Input()
    ticket: Ticket;

    constructor(private tickerService: TicketService) {}

    saveComment() {
        this.ticket.inProgress = true;
        this.tickerService.updateComment(this.ticket.id, this.ticket.number, this.ticket.comment)
            .subscribe(() => this.ticket.inProgress = false);
    }

    clearComment() {
        this.ticket.comment = null;
        this.saveComment();
    }

    removeTicket(id: number) {
        this.tickerService.removeTicket(id).subscribe();
    }

}