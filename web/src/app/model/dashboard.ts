import {DashboardColumn} from "./dashboard-column";
import {Attribute} from "./attribute";

export class Dashboard {
    id: number;

    name: string;

    query: string;

    columns: DashboardColumn[] = [];

    private columnIndex: Map<number, DashboardColumn> = new Map<number, DashboardColumn>();

    get(attribute: Attribute): DashboardColumn {
        return this.columnIndex.get(attribute.id);
    }

    getFullWidth(): number {
        if (this.columns.length == 0) {
            return 0;
        }
        return this.columns
            .map(column => column.getFullWidth())
            .reduce((prev, current) => prev + current);
    }

    static from(value: any): Dashboard {
        let attribute = new Dashboard();
        attribute.id = value.id;
        attribute.name = value.name;
        attribute.query = value.query;
        for (let entry of value.columns) {
            let column = DashboardColumn.from(entry);
            attribute.columns.push(column);
            attribute.columnIndex.set(column.attribute.id, column);
        }
        return attribute;
    }

    toSave(): any {
        return {
            id: this.id,
            name: this.name,
            query: this.query,
            columns: this.columns.map(column => column.toSave())
        };
    }

}