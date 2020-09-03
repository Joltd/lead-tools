import {Attribute} from "./attribute";

export class DashboardColumn {
    id: number;

    attribute: Attribute;

    order: Order = 'NONE';

    position: number = 9999;

    width: number = 200;

    widthDelta: number = 0;

    getFullWidth() {
        return this.width + this.widthDelta;
    }

    static fromAttribute(attribute: Attribute): DashboardColumn {
        let column = new DashboardColumn();
        column.attribute = attribute;
        return column;
    }

    static from(value: any): DashboardColumn {
        let column = new DashboardColumn();
        column.id = value.id;
        column.attribute = Attribute.from(value.attribute);
        column.order = value.order;
        column.position = value.position;
        column.width = value.width;
        return column;
    }

    toSave(): any {
        return {
            id: this.id,
            attribute: this.attribute.toSave(),
            order: this.order,
            position: this.position,
            width: this.width
        }
    }

}

type Order = 'ASC' | 'DESC' | 'NONE'