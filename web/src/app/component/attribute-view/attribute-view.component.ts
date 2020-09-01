import {Component, Input} from "@angular/core";
import {Attribute} from "../../model/attribute";
import {AttributeType} from "../../model/attribute-type";

@Component({
    selector: 'attribute-view',
    templateUrl: 'attribute-view.component.html',
    styleUrls: ['attribute-view.component.scss']
})
export class AttributeViewComponent {

    @Input()
    attribute: Attribute;

    types: AttributeType[] = [
        'STRING',
        'TEXT',
        'NUMBER',
        'DATE',
        'DATETIME',
        'BOOLEAN'
    ];

}