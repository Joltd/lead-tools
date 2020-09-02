import {Component} from "@angular/core";
import {TicketService} from "../../service/ticket.service";

@Component({
    selector: 'ticket-view',
    templateUrl: 'ticket-view.component.html',
    styleUrls: ['ticket-view.component.scss']
})
export class TicketViewComponent {

    constructor(public ticketService: TicketService) {}



}