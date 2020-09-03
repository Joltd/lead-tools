import {Component, Inject, Input, OnInit} from "@angular/core";
import {Attribute} from "../../model/attribute";
import {AttributeType} from "../../model/attribute-type";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AttributeService} from "../../service/attribute.service";

@Component({
    selector: 'attribute-view',
    templateUrl: 'attribute-view.component.html',
    styleUrls: ['attribute-view.component.scss']
})
export class AttributeViewComponent implements OnInit {

    attribute: Attribute;

    types: AttributeType[] = [
        'STRING',
        'TEXT',
        'NUMBER',
        'DATE',
        'DATETIME',
        'BOOLEAN'
    ];

    constructor(
        private attributeService: AttributeService,
        public dialogRef: MatDialogRef<AttributeViewComponent>,
        @Inject(MAT_DIALOG_DATA) public id: number
    ) {}

    ngOnInit(): void {
        this.attribute = new Attribute();
        if (this.id) {
            this.attributeService.loadById(this.id)
                .subscribe(result => this.attribute = result);
        }
    }

    save() {
        this.attributeService.update(this.attribute)
            .subscribe(() => this.dialogRef.close(true));
    }

    cancel() {
        this.dialogRef.close(false);
    }

    delete() {
        this.attributeService.delete(this.attribute.id)
            .subscribe(() => this.dialogRef.close(true));
    }

}