import {Component, OnInit} from "@angular/core";
import {Attribute} from "../../model/attribute";
import {AttributeService} from "../../service/attribute.service";
import {MatDialog} from "@angular/material/dialog";
import {AttributeViewComponent} from "../attribute-view/attribute-view.component";

@Component({
    selector: 'attribute-browser',
    templateUrl: 'attribute-browser.component.html',
    styleUrls: ['attribute-browser.component.scss']
})
export class AttributeBrowserComponent implements OnInit {

    attributes: Attribute[] = [];
    current: Attribute;

    constructor(private attributeService: AttributeService, private dialog: MatDialog) {}

    ngOnInit(): void {
        this.load();
    }

    load() {
        this.attributeService.load().subscribe(result => this.attributes = result);
    }

    add() {
        let dialogRef = this.dialog.open(AttributeViewComponent);
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.load();
            }
        })
    }

    edit(attribute: Attribute) {
        let dialogRef = this.dialog.open(AttributeViewComponent, {data: attribute.id});
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.load();
            }
        })
    }

}