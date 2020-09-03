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

    visibleAttributes(dashboard: Dashboard): TicketAttribute[] {
        return this.attributes.filter(ticketAttribute => dashboard.get(ticketAttribute.attribute))
            .sort((left,right) => dashboard.get(left.attribute).position - dashboard.get(right.attribute).position)
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
    }

}