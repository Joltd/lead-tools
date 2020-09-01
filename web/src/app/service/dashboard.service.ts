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
            .pipe(map(result => result.map(entry => DashboardService.toModel(entry))));
    }

    update(dashboard: Dashboard): Observable<Dashboard> {
        return this.http.post<any>(environment.apiUrl + DashboardService.PATH, DashboardService.toSave(dashboard))
            .pipe(map(result => DashboardService.toModel(result)))
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(environment.apiUrl + DashboardService.PATH + '/' + id);
    }

    private static toModel(entry: any): Dashboard {
        let attribute = new Dashboard();
        attribute.id = entry.id;
        attribute.name = entry.name;
        attribute.query = entry.query;
        attribute.attributes = entry.attributes;
        return attribute;
    }

    private static toSave(dashboard: Dashboard): any {
        return {
            id: dashboard.id,
            name: dashboard.name,
            query: dashboard.query,
            attributes: dashboard.attributes
        };
    }

}