import {Component} from "@angular/core";
import {Ticket} from "../../model/ticket";

@Component({
    selector: 'ticket-view',
    templateUrl: 'ticket-view.component.html',
    styleUrls: ['ticket-view.component.scss']
})
export class TicketViewComponent {

    ticket: Ticket;

}