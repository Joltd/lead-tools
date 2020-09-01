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

    constructor(private http: HttpClient) {}

    load(): Observable<Dashboard[]> {
        return this.http.get<any[]>(environment.apiUrl + DashboardService.PATH)
            .pipe(map(result => result.map(entry => Dashboard.from(entry))));
    }

    update(dashboard: Dashboard): Observable<Dashboard> {
        return this.http.post<any>(environment.apiUrl + DashboardService.PATH, dashboard.toSave())
            .pipe(map(result => Dashboard.from(result)))
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(environment.apiUrl + DashboardService.PATH + '/' + id);
    }

}