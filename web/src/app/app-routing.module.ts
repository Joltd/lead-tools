import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {DashboardBrowserComponent} from "./component/dashboard-browser/dashboard-browser.component";
import {AttributeBrowserComponent} from "./component/attribute-browser/attribute-browser.component";

const routes: Routes = [
  { path: 'attribute', component: AttributeBrowserComponent },
  { path: 'dashboard', component: DashboardBrowserComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
