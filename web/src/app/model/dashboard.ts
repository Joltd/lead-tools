export class Dashboard {
    id: number;

    name: string;

    query: string;

    attributes: number[] = [];

    static from(value: any): Dashboard {
        let attribute = new Dashboard();
        attribute.id = value.id;
        attribute.name = value.name;
        attribute.query = value.query;
        attribute.attributes = value.attributes;
        return attribute;
    }

    toSave(): any {
        return {
            id: this.id,
            name: this.name,
            query: this.query,
            attributes: this.attributes
        };
    }
}