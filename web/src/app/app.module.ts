import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {TicketBrowserComponent} from "./component/ticket-browser/ticket-browser.component";
import {TicketService} from "./service/ticket.service";
import {TicketRowComponent} from "./component/ticket-row/ticket-row.component";
import {ApiInterceptor} from "./service/api-interceptor.service";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {StatusComponent} from "./component/status/status.component";
import {ToolbarComponent} from "./component/toolbar/toolbar.component";

@NgModule({
  declarations: [
    AppComponent,
    TicketBrowserComponent,
    TicketRowComponent,
    StatusComponent,
    ToolbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiInterceptor,
      multi: true
    },
    TicketService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
