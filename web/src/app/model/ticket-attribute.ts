import {Attribute} from "./attribute";

export class TicketAttribute {
    attribute: Attribute;

    value: string;

    static from(value: any): TicketAttribute {
        let ticketAttribute = new TicketAttribute();
        ticketAttribute.attribute = Attribute.from(value.attribute);
        ticketAttribute.value = value.value;
        return ticketAttribute;
    }

    toSave(): any {
        return {
            attribute: this.attribute.toSave(),
            value: this.value
        }
    }
}