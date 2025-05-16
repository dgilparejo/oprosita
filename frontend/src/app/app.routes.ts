import { Routes } from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {GroupsComponent} from './pages/groups/groups.component';
import {MonthDetailComponent} from './pages/monthdetail/monthdetail.component';

export const routes: Routes = [
  { path: '', redirectTo: 'inicio', pathMatch: 'full' },
  { path: 'inicio', component: HomeComponent, title: 'Inicio' },
  { path: 'grupos', component: GroupsComponent, title: 'Grupos' },
  {
    path: 'grupos/:id',
    loadComponent: () => import('./pages/months/months.component').then(m => m.MonthsComponent)
  },
  {
    path: 'grupos/:id/:mes',
    component: MonthDetailComponent
  }
];
