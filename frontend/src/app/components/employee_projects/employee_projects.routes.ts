import { Routes } from '@angular/router';
import { EmployeeProjectsListComponent } from './employee-projects-list/employee-projects-list.component';
import { EmployeeProjectsFormComponent } from './employee-projects-form/employee-projects-form.component';

export const routes: Routes = [
  {
    path: '',
    component: EmployeeProjectsListComponent,
  },
  {
    path: 'new',
    component: EmployeeProjectsFormComponent,
  },
  {
    path: 'edit/:employeeId/:projectId',
    component: EmployeeProjectsFormComponent,
  },
];
