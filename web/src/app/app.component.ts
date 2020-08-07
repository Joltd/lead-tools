import { Component } from '@angular/core';
import {TicketService} from "./service/ticket.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'lead-tools';

  constructor(public ticketService: TicketService) {}
}
