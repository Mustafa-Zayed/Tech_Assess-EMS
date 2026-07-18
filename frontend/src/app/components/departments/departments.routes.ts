import { Routes } from '@angular/router';
import { DepartmentListComponent } from './department-list/department-list.component';
import { DepartmentFormComponent } from './department-form/department-form.component';

export const routes: Routes = [
  {
    path: '',
    component: DepartmentListComponent,
  },
  { path: 'new', component: DepartmentFormComponent },
  { path: 'edit/:id', component: DepartmentFormComponent },
];
