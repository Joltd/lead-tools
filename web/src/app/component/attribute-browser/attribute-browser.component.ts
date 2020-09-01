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
    current: Attribute;

    constructor(private attributeService: AttributeService) {}

    ngOnInit(): void {
        this.load();
    }

    load() {
        this.attributeService.load().subscribe(result => this.attributes = result);
    }

    new() {
        if (this.current && !this.current.id) {
            return
        }
        let attribute = new Attribute();
        this.attributes.push(attribute);
        this.edit(attribute);
    }

    isCurrent(attribute: Attribute) {
        return this.current && this.current.id == attribute.id;
    }

    edit(attribute: Attribute) {
        this.current = attribute;
    }

    delete(id: number) {
        this.attributeService.delete(id).subscribe(() => this.load());
    }

    save() {
        if (!this.current) {
            return;
        }

        this.attributeService.update(this.current)
            .subscribe(() => {
                this.cancel();
                this.load();
            })
    }

    cancel() {
        this.current = null;
        this.attributes = this.attributes.filter(attribute => attribute.id);
    }

}