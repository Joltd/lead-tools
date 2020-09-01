import {Injectable} from "@angular/core";
import {EMPTY, Observable, throwError} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Ticket} from "../model/ticket";
import {environment} from "../../environments/environment";
import {catchError, map, tap} from "rxjs/operators";
import {Dashboard} from "../model/dashboard";
import {TicketAttribute} from "../model/ticket-attribute";
import {Attribute} from "../model/attribute";

@Injectable()
export class TicketService {

    private static readonly PATH = '/ticket';

    private _dashboard: Dashboard;
    private _tickets: Ticket[];

    constructor(private http: HttpClient) {}

    get dashboard(): Dashboard {
        return this._dashboard;
    }

    set dashboard(value: Dashboard) {
        this._dashboard = value;
    }

    get tickets(): Ticket[] {
        return this._tickets;
        // return this._tickets.filter(ticket => {
        //     return (ticket.assignee && ticket.assignee.indexOf( this._assignee) >= 0)
        //         || ticket.sub.find(ticket => ticket.assignee && ticket.assignee.indexOf(this._assignee) >= 0)
        // })
    }

    loadTickets(): Observable<Ticket[]> {
        if (!this.dashboard) {
            return EMPTY;
        }

        return this.http.get<any[]>(environment.apiUrl + TicketService.PATH)
            .pipe(map(result => this._tickets = result.map(entry => Ticket.from(entry))))
    }

    trackTicket(number: string): Observable<void> {
        return this.http.post<void>(environment.apiUrl + '/ticket/track/' + number, null).pipe(tap(() => this.loadTickets().subscribe()));
    }

    removeTicket(id: number): Observable<void> {
        return this.http.delete<void>(environment.apiUrl + '/ticket/' + id)
            .pipe(() => {
                this.loadTickets().subscribe();
                return null;
            });
    }

}
