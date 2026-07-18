import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { ProjectService } from '../../../services/project.service';
import { DepartmentService } from '../../../services/department.service';

import { ProjectDTO } from '../../../models/project.dto';
import { DepartmentDTO } from '../../../models/department.dto';

import { Subscription } from 'rxjs';

@Component({
  selector: 'app-project-form',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './project-form.component.html',
  styleUrl: './project-form.component.css',
})
export class ProjectFormComponent {
  private fb = inject(FormBuilder);
  private projectService = inject(ProjectService);
  private departmentService = inject(DepartmentService);
  private router = inject(Router);
  private activatedRoute = inject(ActivatedRoute);

  private subscriptions: Subscription[] = [];

  isEditMode = signal(false);
  projectId!: number;

  loading = signal(false);
  saving = signal(false);
  errorMessage = signal('');

  departments = signal<DepartmentDTO[]>([]);

  projectForm = this.fb.nonNullable.group({
    name: ['', Validators.required],
    description: ['', Validators.required],
    startDate: ['', Validators.required],
    endDate: ['', Validators.required],
    departmentId: [0, [Validators.required, Validators.min(1)]],
  });

  ngOnInit(): void {
    this.loadDepartments();

    const id = this.activatedRoute.snapshot.paramMap.get('id');

    if (id) {
      this.isEditMode.set(true);
      this.projectId = Number(id);
      this.loadProject();
    }
  }

  loadDepartments(): void {
    const sub = this.departmentService.getAllDepartments().subscribe({
      next: (response) => {
        this.departments.set(response.data);
      },
      error: (err) => {
        this.errorMessage.set(err.error?.message ?? 'Failed to load departments.');
      },
    });

    this.subscriptions.push(sub);
  }

  loadProject(): void {
    this.loading.set(true);
    this.errorMessage.set('');

    const sub = this.projectService.getProjectById(this.projectId).subscribe({
      next: (response) => {
        this.loading.set(false);

        this.projectForm.patchValue({
          name: response.data.name,
          description: response.data.description,
          startDate: response.data.startDate.substring(0, 10),
          endDate: response.data.endDate.substring(0, 10),
          departmentId: response.data.departmentId,
        });
      },
      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.error?.message ?? 'Failed to load project.');
      },
    });

    this.subscriptions.push(sub);
  }

  saveProject(): void {
    if (this.projectForm.invalid) {
      this.projectForm.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    this.errorMessage.set('');

    const project: ProjectDTO = this.projectForm.getRawValue();

    project.startDate = `${project.startDate}T00:00:00`;
    project.endDate = `${project.endDate}T00:00:00`;

    if (this.isEditMode()) {
      const sub = this.projectService.updateProject(this.projectId, project).subscribe({
        next: () => {
          this.saving.set(false);
          this.router.navigate(['/projects']);
        },
        error: (err) => {
          this.saving.set(false);
          this.errorMessage.set(err.error?.message ?? 'Failed to update project.');
        },
      });

      this.subscriptions.push(sub);
    } else {
      const sub = this.projectService.createProject(project).subscribe({
        next: () => {
          this.saving.set(false);
          this.router.navigate(['/projects']);
        },
        error: (err) => {
          this.saving.set(false);
          this.errorMessage.set(err.error?.message ?? 'Failed to create project.');
        },
      });

      this.subscriptions.push(sub);
    }
  }

  get f() {
    return this.projectForm.controls;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
