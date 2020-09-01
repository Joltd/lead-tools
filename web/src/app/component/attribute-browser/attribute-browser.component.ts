import {Component, OnInit} from "@angular/core";
import {Attribute} from "../../model/attribute";
import {AttributeService} from "../../service/attribute.service";

@Component({
    selector: 'attribute-browser',
    templateUrl: 'attribute-browser.component.html',
    styleUrls: ['attribute-browser.component.scss']
})
export class AttributeBrowserComponent implements OnInit {

    attributes: Attribute[] = [];
    editing: Attribute;

    constructor(private attributeService: AttributeService) {}

    ngOnInit(): void {
        this.load();
    }

    load() {
        this.attributeService.load().subscribe(result => this.attributes = result);
    }

    new() {
        if (this.editing && !this.editing.id) {
            return
        }
        let attribute = new Attribute();
        this.attributes.push(attribute);
        this.edit(attribute);
    }

    isEditing(attribute: Attribute) {
        return this.editing && this.editing.id == attribute.id;
    }

    edit(attribute: Attribute) {
        this.editing = attribute;
    }

    delete(id: number) {
        this.attributeService.delete(id).subscribe(() => this.load());
    }

    save() {
        if (!this.editing) {
            return;
        }

        this.attributeService.update(this.editing)
            .subscribe(() => {
                this.cancel();
                this.load();
            })
    }

    cancel() {
        this.editing = null;
        this.attributes = this.attributes.filter(attribute => attribute.id);
    }

}