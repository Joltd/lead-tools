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

    saveComment() {}

    clearComment() {}

    removeTicket(id: number) {
        this.tickerService.removeTicket(id).subscribe();
    }

}