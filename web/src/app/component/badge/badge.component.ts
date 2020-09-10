import {Component, Input} from "@angular/core";

@Component({
    selector: 'badge',
    templateUrl: 'badge.component.html',
    styleUrls: ['badge.component.scss']
})
export class BadgeComponent {

    public static readonly TYPES: string[] = [
        'green',
        'blue',
        'yellow',
        'gray',
        'red',
        'azure',
        'light',
        'dark'
    ];

    @Input()
    value: string;

    @Input()
    type: string = 'light';
}