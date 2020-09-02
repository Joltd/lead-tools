import {Component, Input} from "@angular/core";
import {Attribute} from "../../model/attribute";

@Component({
    selector: 'attribute',
    templateUrl: 'attribute.component.html',
    styleUrls: ['attribute.component.scss']
})
export class AttributeComponent {

    @Input()
    attribute: Attribute;

    @Input()
    value: string;

    @Input()
    edit: boolean = false;

}