import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { DepartmentService } from '../../../services/department.service';
import { DepartmentDTO } from '../../../models/department.dto';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-department-form',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './department-form.component.html',
  styleUrl: './department-form.component.css',
})
export class DepartmentFormComponent {
  private fb = inject(FormBuilder);
  private departmentService = inject(DepartmentService);
  private router = inject(Router);
  private activatedRoute = inject(ActivatedRoute);

  private subscriptions: Subscription[] = [];

  isEditMode = signal<boolean>(false);
  departmentId!: number;

  loading = signal<boolean>(false);
  saving = signal<boolean>(false);
  errorMessage = signal<string>('');

  departmentForm = this.fb.nonNullable.group({
    name: ['', [Validators.required]],
    location: ['', [Validators.required]],
    budget: [1, [Validators.required, Validators.min(1)]],
  });

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.paramMap.get('id');

    if (id) {
      this.isEditMode.set(true);
      this.departmentId = Number(id);
      this.loadDepartment();
    }
  }

  loadDepartment(): void {
    this.loading.set(true);
    this.errorMessage.set('');

    const sub = this.departmentService.getDepartmentById(this.departmentId).subscribe({
      next: (response) => {
        this.loading.set(false);

        this.departmentForm.patchValue({
          name: response.data.name,
          location: response.data.location,
          budget: response.data.budget,
        });
      },

      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.error?.message ?? 'Failed to load department.');
      },
    });
    this.subscriptions.push(sub);
  }

  saveDepartment(): void {
    if (this.departmentForm.invalid) {
      this.departmentForm.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    this.errorMessage.set('');

    const department: DepartmentDTO = this.departmentForm.getRawValue();

    if (this.isEditMode()) {
      const sub = this.departmentService.updateDepartment(this.departmentId, department).subscribe({
        next: () => {
          this.saving.set(false);
          this.router.navigate(['/departments']);
        },

        error: (err) => {
          this.saving.set(false);
          this.errorMessage.set(err.error?.message ?? 'Failed to update department.');
        },
      });
      this.subscriptions.push(sub);
    } else {
      const sub = this.departmentService.createDepartment(department).subscribe({
        next: () => {
          this.saving.set(false);
          this.router.navigate(['/departments']);
        },

        error: (err) => {
          this.saving.set(false);
          this.errorMessage.set(err.error?.message ?? 'Failed to create department.');
        },
      });
      this.subscriptions.push(sub);
    }
  }

  get f() {
    return this.departmentForm.controls;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
