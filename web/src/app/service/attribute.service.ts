import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Attribute} from "../model/attribute";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";

@Injectable()
export class AttributeService {

    private static readonly PATH = '/attribute';

    constructor(private http: HttpClient) {}

    load(): Observable<Attribute[]> {
        return this.http.get<any[]>(environment.apiUrl + AttributeService.PATH)
            .pipe(map(result => result.map(entry => Attribute.from(entry))));
    }

    loadById(id: number): Observable<Attribute> {
        return this.http.get<any>(environment.apiUrl + AttributeService.PATH + '/' + id)
            .pipe(map(result => Attribute.from(result)));
    }

    update(attribute: Attribute): Observable<Attribute> {
        return this.http.post<any>(environment.apiUrl + AttributeService.PATH, attribute.toSave())
            .pipe(map(result => Attribute.from(result)))
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(environment.apiUrl + AttributeService.PATH + '/' + id);
    }

}