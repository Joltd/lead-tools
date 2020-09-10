export class AttributeColor {

    id: number;

    color: string;

    condition: string;

    static from(value: any): AttributeColor {
        let attributeColor = new AttributeColor();
        attributeColor.id = value.id;
        attributeColor.color = value.color;
        attributeColor.condition = value.condition;
        return attributeColor;
    }

    toSave(): any {
        return {
            id: this.id,
            color: this.color,
            condition: this.condition
        }
    }

}