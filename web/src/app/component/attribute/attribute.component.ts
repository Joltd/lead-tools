import {Component, EventEmitter, Input, Output} from "@angular/core";
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
    color: string;

    @Output()
    onValue: EventEmitter<string> = new EventEmitter<string>();

    @Input()
    edit: boolean = false;

}