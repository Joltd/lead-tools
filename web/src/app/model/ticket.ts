import {TicketAttribute} from "./ticket-attribute";
import {Dashboard} from "./dashboard";
import {Attribute} from "./attribute";

export class Ticket {

    id: number;

    attributes: TicketAttribute[] = [];

    private attributeIndex: Map<number,TicketAttribute> = new Map<number, TicketAttribute>();

    get(attribute: Attribute) {
        return this.attributeIndex.get(attribute.id);
    }

    add(ticketAttribute: TicketAttribute) {
        this.attributes.push(ticketAttribute);
        this.attributeIndex.set(ticketAttribute.attribute.id, ticketAttribute);
    }

    static from(value): Ticket {
        let ticket = new Ticket();
        ticket.id = value.id;
        for (let entry of value.attributes) {
            let attribute = TicketAttribute.from(entry);
            ticket.add(attribute);
        }
        return ticket;
    }

    toSave(): any {
        return {
            id: this.id,
            attributes: this.attributes.map(entry => entry.toSave())
        }
    }

    addAttributesFromDashboard(dashboard: Dashboard) {
        for (let column of dashboard.columns) {
            let ticketAttribute = this.get(column.attribute);
            if (!ticketAttribute) {
                ticketAttribute = new TicketAttribute();
                ticketAttribute.attribute = column.attribute;
                this.add(ticketAttribute);
            }
        }
        this.reorder(dashboard);
    }

    reorder(dashboard: Dashboard) {
        let attributes = new Array<TicketAttribute>(this.attributes.length);
        for (let attribute of this.attributes) {
            let column = dashboard.get(attribute.attribute);
            attributes[column.position] = attribute;
        }
        this.attributes = attributes;
    }
}