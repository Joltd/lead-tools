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
import {AttributeService} from "./service/attribute.service";
import {DashboardService} from "./service/dashboard.service";
import {MainMenuComponent} from "./component/main-menu/main-menu.component";
import {DashboardBrowserComponent} from "./component/dashboard-browser/dashboard-browser.component";
import {AttributeBrowserComponent} from "./component/attribute-browser/attribute-browser.component";
import {DashboardViewComponent} from "./component/dashboard-view/dashboard-view.component";

@NgModule({
  declarations: [
    AppComponent,
    MainMenuComponent,
    AttributeBrowserComponent,
    DashboardBrowserComponent,
    DashboardViewComponent,
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
    AttributeService,
    DashboardService,
    TicketService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
