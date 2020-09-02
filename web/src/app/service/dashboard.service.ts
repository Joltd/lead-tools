import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Attribute} from "../model/attribute";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";
import {Dashboard} from "../model/dashboard";

@Injectable()
export class DashboardService {

    private static readonly PATH = '/dashboard';

    private _dashboards: Dashboard[] = [];
    private _current: Dashboard;

    constructor(private http: HttpClient) {}

    get dashboards(): Dashboard[] {
        return this._dashboards;
    }

    get current(): Dashboard {
        return this._current;
    }

    set current(value: Dashboard) {
        this._current = value;
    }

    load(): Observable<Dashboard[]> {
        return this.http.get<any[]>(environment.apiUrl + DashboardService.PATH)
            .pipe(map(result => {
                this._dashboards = result.map(entry => Dashboard.from(entry));
                if (this._dashboards.length > 0 && !this._current) {
                    this._current = this._dashboards[0];
                }
                return this._dashboards;
            }));
    }

    update(dashboard: Dashboard): Observable<Dashboard> {
        return this.http.post<any>(environment.apiUrl + DashboardService.PATH, dashboard.toSave())
            .pipe(map(result => Dashboard.from(result)))
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(environment.apiUrl + DashboardService.PATH + '/' + id);
    }

}