import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { Subscription } from 'rxjs';

import { EmployeeProjectService } from '../../../services/employee-project.service';
import { EmployeeService } from '../../../services/employee.service';
import { ProjectService } from '../../../services/project.service';

import { EmployeeProjectDTO } from '../../../models/employee_project.dto';
import { EmployeeDTO } from '../../../models/employee.dto';
import { ProjectDTO } from '../../../models/project.dto';

import { ProjectRole } from '../../../models/project-role.enum';

@Component({
  selector: 'app-employee-projects-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './employee-projects-form.component.html',
  styleUrl: './employee-projects-form.component.css',
})
export class EmployeeProjectsFormComponent {
  private fb = inject(FormBuilder);
  private employeeProjectService = inject(EmployeeProjectService);
  private employeeService = inject(EmployeeService);
  private projectService = inject(ProjectService);
  private router = inject(Router);
  private activatedRoute = inject(ActivatedRoute);

  private subscriptions: Subscription[] = [];

  isEditMode = signal(false);

  employeeId!: number;
  projectId!: number;

  loading = signal(false);
  saving = signal(false);
  errorMessage = signal('');

  employees = signal<EmployeeDTO[]>([]);
  projects = signal<ProjectDTO[]>([]);

  roles = Object.values(ProjectRole);

  employeeProjectForm = this.fb.nonNullable.group({
    employeeId: [0, [Validators.required, Validators.min(1)]],
    projectId: [0, [Validators.required, Validators.min(1)]],
    role: [ProjectRole.DEVELOPER, [Validators.required]],
  });

  ngOnInit(): void {
    this.loadEmployees();
    this.loadProjects();

    const employeeId = this.activatedRoute.snapshot.paramMap.get('employeeId');
    const projectId = this.activatedRoute.snapshot.paramMap.get('projectId');

    if (employeeId && projectId) {
      this.isEditMode.set(true);

      this.employeeId = Number(employeeId);
      this.projectId = Number(projectId);

      this.loadEmployeeProject();
    }
  }

  loadEmployees(): void {
    const sub = this.employeeService.getAllEmployees().subscribe({
      next: (response) => this.employees.set(response.data),
      error: (err) => this.errorMessage.set(err.error?.message ?? 'Failed to load employees.'),
    });
    this.subscriptions.push(sub);
  }

  loadProjects(): void {
    const sub = this.projectService.getAllProjects().subscribe({
      next: (response) => this.projects.set(response.data),
      error: (err) => this.errorMessage.set(err.error?.message ?? 'Failed to load projects.'),
    });
    this.subscriptions.push(sub);
  }

  loadEmployeeProject(): void {
    this.loading.set(true);
    this.errorMessage.set('');

    const sub = this.employeeProjectService
      .getEmployeeProject(this.employeeId, this.projectId)
      .subscribe({
        next: (response) => {
          this.loading.set(false);

          this.employeeProjectForm.patchValue({
            employeeId: response.data.employeeId!,
            projectId: response.data.projectId!,
            role: response.data.role,
          });

          // Disable in edit mode so that the user cannot choose different employee or project
          // that may be not existed
          this.f.employeeId.disable();
          this.f.projectId.disable();
        },
        error: (err) => {
          this.loading.set(false);
          this.errorMessage.set(err.error?.message ?? 'Failed to load assignment.');
        },
      });
    this.subscriptions.push(sub);
  }

  saveEmployeeProject(): void {
    if (this.employeeProjectForm.invalid) {
      this.employeeProjectForm.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    this.errorMessage.set('');

    const employeeProject: EmployeeProjectDTO = this.employeeProjectForm.getRawValue();

    // In create mode, employeeId and projectId are not set in the url,
    // so we need to get them from the form values
    const employeeId = employeeProject.employeeId!;
    const projectId = employeeProject.projectId!;

    if (this.isEditMode()) {
      const sub = this.employeeProjectService
        .updateEmployeeProject(this.employeeId, this.projectId, employeeProject)
        .subscribe({
          next: () => {
            this.saving.set(false);
            this.router.navigate(['/employee-projects']);
          },
          error: (err) => {
            this.saving.set(false);
            this.errorMessage.set(err.error?.message ?? 'Failed to update assignment.');
          },
        });
      this.subscriptions.push(sub);
    } else {
      const sub = this.employeeProjectService
        .assignEmployeeToProject(employeeId, projectId, employeeProject)
        .subscribe({
          next: () => {
            this.saving.set(false);
            this.router.navigate(['/employee-projects']);
          },
          error: (err) => {
            this.saving.set(false);
            this.errorMessage.set(err.error?.message ?? 'Failed to create assignment.');
          },
        });

      this.subscriptions.push(sub);
    }
  }

  get f() {
    return this.employeeProjectForm.controls;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
