import { Routes } from '@angular/router';
import { ProjectListComponent } from './project-list/project-list.component';
import { ProjectFormComponent } from './project-form/project-form.component';

export const routes: Routes = [
  {
    path: '',
    component: ProjectListComponent,
  },
  {
    path: 'new',
    component: ProjectFormComponent,
  },
  {
    path: 'edit/:id',
    component: ProjectFormComponent,
  },
];
