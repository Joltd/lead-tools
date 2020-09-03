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
import {DashboardBrowserComponent} from "./component/dashboard-browser/dashboard-browser.component";
import {AttributeBrowserComponent} from "./component/attribute-browser/attribute-browser.component";
import {AttributeViewComponent} from "./component/attribute-view/attribute-view.component";
import {TicketViewComponent} from "./component/ticket-view/ticket-view.component";
import {TicketHeaderComponent} from "./component/ticket-header/ticket-header.component";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {MatCardModule} from "@angular/material/card";
import {ScrollingModule} from "@angular/cdk/scrolling";
import {AttributeComponent} from "./component/attribute/attribute.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatChipsModule} from "@angular/material/chips";
import {MatTabsModule} from "@angular/material/tabs";
import {DashboardViewComponent} from "./component/dashboard-view/dashboard-view.component";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";

@NgModule({
    declarations: [
        AppComponent,
        AttributeComponent,
        AttributeBrowserComponent,
        AttributeViewComponent,
        DashboardBrowserComponent,
        DashboardViewComponent,
        TicketBrowserComponent,
        TicketHeaderComponent,
        TicketRowComponent,
        TicketViewComponent,
        StatusComponent,
        ToolbarComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        BrowserAnimationsModule,
        MatSidenavModule,
        MatToolbarModule,
        MatIconModule,
        MatButtonModule,
        MatListModule,
        DragDropModule,
        MatCardModule,
        ScrollingModule,
        MatFormFieldModule,
        MatInputModule,
        MatCheckboxModule,
        MatChipsModule,
        MatTabsModule,
        MatDialogModule
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
