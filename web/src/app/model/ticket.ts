export class Ticket {
    id: number;

    number: string;

    title: string;

    assignee: string;

    status: string;

    comment: string;

    sub: Ticket[] = [];

    subVisible: boolean = false;

    inProgress: boolean = true;

    tracked: boolean = false;



}