import {Injectable} from '@angular/core';
import {ErrorInfo} from "../model/error-info";

@Injectable({providedIn: 'root'})
export class ErrorHubService {

    errors: ErrorInfo[] = [];

    registerError(error: any) {
        this.errors.push(new ErrorInfo(error));
        alert(error.message);
    }

    clear() {
        this.errors = [];
    }
}
