import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'departments',
    loadComponent: () =>
      import('./components/departments/department-list.component').then(
        (m) => m.DepartmentListComponent,
      ),
  },
];
