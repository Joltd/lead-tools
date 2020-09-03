import {DashboardColumn} from "./dashboard-column";

export class Dashboard {
    id: number;

    name: string;

    query: string;

    columns: DashboardColumn[] = [];

    static from(value: any): Dashboard {
        let attribute = new Dashboard();
        attribute.id = value.id;
        attribute.name = value.name;
        attribute.query = value.query;
        attribute.columns = value.columns.map(entry => DashboardColumn.from(entry));
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