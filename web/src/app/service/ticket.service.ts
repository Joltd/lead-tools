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

    private _tickets: Ticket[] = [];
    private _current: Ticket

    constructor(
        private http: HttpClient,
        private dashboardService: DashboardService
    ) {}

    get tickets(): Ticket[] {
        return this._tickets;
    }

    get current(): Ticket {
        return this._current;
    }

    set current(value: Ticket) {
        this._current = value;
    }

    load(): Observable<Ticket[]> {
        let dashboard = this.dashboardService.current;
        let params = new HttpParams();
        params.append('dashboard', dashboard.id.toString());
        return this.http.get<any[]>(environment.apiUrl + TicketService.PATH, {params})
            .pipe(map(result => this._tickets = result.map(entry => Ticket.from(entry))))
    }

    loadById(id: number): Observable<Ticket> {
        return this.http.get<any>(environment.apiUrl + TicketService.PATH + '/' + id)
            .pipe(map(result => this._current = Ticket.from(result)));
    }

    update(): Observable<Ticket> {
        let toSave = this._current.toSave();
        return this.http.post(environment.apiUrl + TicketService.PATH, toSave)
            .pipe(map(result => this._current = Ticket.from(result)));
    }

    delete(id: number): Observable<void> {
        return this.http.delete(environment.apiUrl + TicketService.PATH + '/' + id)
            .pipe(() => null);
    }

}
