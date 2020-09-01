import {TicketAttribute} from "./ticket-attribute";

export class Ticket {

    id: number;

    number: string;

    attributes: TicketAttribute[] = [];

    static from(value): Ticket {
        let ticket = new Ticket();
        ticket.id = value.id;
        ticket.number = value.number;
        ticket.attributes = value.attributes.map(entry => TicketAttribute.from(entry));
        return ticket;
    }

}