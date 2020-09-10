import {AttributeType} from "./attribute-type";
import {AttributeColor} from "./attribute-color";

export class Attribute {

    id: number;

    name: string;

    type: AttributeType;

    readonly: boolean;

    link: string;

    colors: AttributeColor[] = [];

    static from(value: any): Attribute {
        let attribute = new Attribute();
        attribute.id = value.id;
        attribute.name = value.name;
        attribute.type = value.type;
        attribute.readonly = value.readonly;
        attribute.link = value.link;
        for (let entry of value.colors) {
            let color = AttributeColor.from(entry);
            attribute.colors.push(color);
        }
        return attribute;
    }

    toSave(): any {
        return {
            id: this.id,
            name: this.name,
            type: this.type,
            readonly: this.readonly,
            link: this.link,
            colors: this.colors.map(entry => entry.toSave())
        }
    }
}