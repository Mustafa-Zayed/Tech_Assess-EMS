import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'departments',
    loadChildren: () => import('./components/departments/departments.routes').then((m) => m.routes),
  },
  {
    path: 'employees',
    loadChildren: () => import('./components/employees/employees.routes').then((m) => m.routes),
  },
];
