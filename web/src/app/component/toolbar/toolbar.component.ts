import {Component, Input} from "@angular/core";
import {TicketService} from "../../service/ticket.service";

@Component({
    selector: 'toolbar',
    templateUrl: 'toolbar.component.html',
    styleUrls: ['toolbar.component.scss']
})
export class ToolbarComponent {

    number: string;

    constructor(public ticketService: TicketService) {}

    // addTicket() {
    //     this.ticketService.trackTicket(this.number).subscribe();
    // }
    //
    // reload() {
    //     this.ticketService.loadTickets().subscribe();
    // }

}