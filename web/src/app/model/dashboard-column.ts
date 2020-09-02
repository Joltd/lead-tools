import {Attribute} from "./attribute";

export class DashboardColumn {
    attribute: Attribute;

    order: Order;

    position: number;

    width: number;

    widthDelta: number = 0;

    getFullWidth() {
        return this.width + this.widthDelta;
    }

    static from(value: any): DashboardColumn {
        let column = new DashboardColumn();
        column.attribute = Attribute.from(value.attribute);
        column.order = value.order;
        column.position = value.position;
        column.width = value.width;
        return column;
    }

    toSave(): any {
        return {
            attribute: this.attribute.toSave(),
            order: this.order,
            position: this.position
        }
    }

}

type Order = 'ASC' | 'DESC' | 'NONE'