import {TicketAttribute} from "./ticket-attribute";

export class Ticket {

    id: number;

    attributes: TicketAttribute[] = [];

    static from(value): Ticket {
        let ticket = new Ticket();
        ticket.id = value.id;
        ticket.attributes = value.attributes.map(entry => TicketAttribute.from(entry));
        return ticket;
    }

    toSave(): any {
        return {
            id: this.id,
            attributes: this.attributes.map(entry => entry.toSave())
        }
    }
}