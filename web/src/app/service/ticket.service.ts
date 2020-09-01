import {Injectable} from "@angular/core";
import {Observable, throwError} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Ticket} from "../model/ticket";
import {environment} from "../../environments/environment";
import {catchError, map, tap} from "rxjs/operators";

@Injectable()
export class TicketService {

    private _tickets: Ticket[];
    private _assignee: string;
    private _loading: boolean = false;

    constructor(private http: HttpClient) {}

    get tickets(): Ticket[] {
        if (!this._assignee) {
            return this._tickets;
        }
        return this._tickets.filter(ticket => {
            return (ticket.assignee && ticket.assignee.indexOf( this._assignee) >= 0)
                || ticket.sub.find(ticket => ticket.assignee && ticket.assignee.indexOf(this._assignee) >= 0)
        })
    }

    get assignee(): string {
        return this._assignee;
    }

    set assignee(value: string) {
        this._assignee = value;
    }

    get loading(): boolean {
        return this._loading;
    }

    loadTickets(): Observable<Ticket[]> {
        this._loading = true;
        return this.http.get<any[]>(environment.apiUrl + '/ticket')
            .pipe(
                map(result => {
                    this._loading = false;
                    this._tickets = [];
                    for (let entry of result) {
                        let ticket = TicketService.toTicket(entry)
                        this._tickets.push(ticket);
                        this.loadJiraTicket(ticket.number).subscribe(result => {
                            ticket.title = result.title;
                            ticket.assignee = result.assignee;
                            ticket.status = result.status;
                            ticket.inProgress = false;
                        })
                    }
                    return this._tickets;
                }),
                catchError(error => {
                    this._loading = false;
                    return throwError(error);
                })
            )
    }

    private loadJiraTicket(number: String): Observable<any> {
        return this.http.get<any>(environment.apiUrl + '/ticket/jira/' + number);
    }

    updateComment(id: number, number: string, comment: string): Observable<void> {
        let ticket = new Ticket();
        ticket.id = id;
        ticket.number = number;
        ticket.comment = comment;
        return this.http.post<void>(environment.apiUrl + '/ticket/comment', ticket);
    }

    trackTicket(number: string): Observable<void> {
        return this.http.post<void>(environment.apiUrl + '/ticket/track/' + number, null).pipe(tap(() => this.loadTickets().subscribe()));
    }

    removeTicket(id: number): Observable<void> {
        return this.http.delete<void>(environment.apiUrl + '/ticket/' + id)
            .pipe(tap(() => this._tickets = this._tickets.filter(ticket => ticket.id != id)));
    }

    private static toTicket(entry): Ticket {
        let ticket = new Ticket();
        ticket.id = entry.id;
        ticket.number = entry.number;
        ticket.title = entry.title;
        ticket.assignee = entry.assignee;
        ticket.status = entry.status;
        ticket.comment = entry.comment;
        ticket.tracked = entry.tracked;
        if (entry.sub) {
            ticket.sub = entry.sub.map(e => this.toTicket(e));
        }
        return ticket;
    }

}
