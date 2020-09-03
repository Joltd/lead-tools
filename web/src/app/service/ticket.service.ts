import {Injectable} from "@angular/core";
import {EMPTY, Observable, throwError} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Ticket} from "../model/ticket";
import {environment} from "../../environments/environment";
import {catchError, map, tap} from "rxjs/operators";
import {Dashboard} from "../model/dashboard";
import {TicketAttribute} from "../model/ticket-attribute";
import {Attribute} from "../model/attribute";
import {DashboardService} from "./dashboard.service";

@Injectable()
export class TicketService {

    private static readonly PATH = '/ticket';

    constructor(private http: HttpClient) {}

    load(dashboard: Dashboard): Observable<Ticket[]> {
        let params = new HttpParams().append('dashboard', dashboard.id.toString());
        return this.http.get<any[]>(environment.apiUrl + TicketService.PATH, {params})
            .pipe(map(result => result.map(entry => Ticket.from(entry))))
    }

    loadById(id: number): Observable<Ticket> {
        return this.http.get<any>(environment.apiUrl + TicketService.PATH + '/' + id)
            .pipe(map(result => Ticket.from(result)));
    }

    update(ticket: Ticket): Observable<Ticket> {
        let toSave = ticket.toSave();
        return this.http.post(environment.apiUrl + TicketService.PATH, toSave)
            .pipe(map(result => Ticket.from(result)));
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(environment.apiUrl + TicketService.PATH + '/' + id);
    }

}
