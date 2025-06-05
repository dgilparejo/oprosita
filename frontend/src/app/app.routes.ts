import { Routes } from '@angular/router';
import {HomeComponent} from './pages/home/home.component';
import {GroupsComponent} from './pages/groups/groups.component';
import {MonthDetailComponent} from './pages/monthdetail/monthdetail.component';
import {StudentBodyComponent} from './pages/student-body/student-body.component';

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
  },
  {
    path: 'alumnado',
    component: StudentBodyComponent,
    title: 'Alumnado'
  },
  {
    path: 'alumnado/:id',
    loadComponent: () => import('./pages/students/students.component').then(m => m.StudentsComponent),
    title: 'Alumnado'
  },
  {
    path: 'simulacros',
    loadComponent: () => import('./pages/simulation/simulation.component').then(m => m.SimulationComponent),
    title: 'Simulacros'
  },
  {
    path: 'noticias',
    loadComponent: () => import('./pages/news/news.component').then(m => m.NewsComponent),
    title: 'Noticias'
  }
];
