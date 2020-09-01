import {AttributeType} from "./attribute-type";

export class Attribute {

    id: number;

    name: string;

    type: AttributeType;

    readonly: boolean;

    static from(value: any): Attribute {
        let attribute = new Attribute();
        attribute.id = value.id;
        attribute.name = value.name;
        attribute.type = value.type;
        attribute.readonly = value.readonly;
        return attribute;
    }

    toSave(): any {
        return {
            id: this.id,
            name: this.name,
            type: this.type,
            readonly: this.readonly
        }
    }
}