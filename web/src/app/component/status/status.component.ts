import {Component, Input} from "@angular/core";

@Component({
    selector: 'status',
    templateUrl: 'status.component.html',
    styleUrls: ['status.component.scss']
})
export class StatusComponent {

    @Input()
    status: string

    getClass() {
        switch (this.status) {
            case 'Resolved': return 'badge-success'
            case 'Verified':
            case 'In Progress':
            case 'Under QA':
                return 'badge-warning'
            default: return 'badge-info'
        }
    }

}