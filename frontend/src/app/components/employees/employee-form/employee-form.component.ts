import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';

import { EmployeeService } from '../../../services/employee.service';
import { DepartmentService } from '../../../services/department.service';

import { EmployeeDTO } from '../../../models/employee.dto';
import { DepartmentDTO } from '../../../models/department.dto';

@Component({
  selector: 'app-employee-form',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './employee-form.component.html',
  styleUrl: './employee-form.component.css',
})
export class EmployeeFormComponent implements OnInit {
  private fb = inject(FormBuilder);
  private employeeService = inject(EmployeeService);
  private departmentService = inject(DepartmentService);
  private router = inject(Router);
  private activatedRoute = inject(ActivatedRoute);

  isEditMode = signal(false);
  employeeId!: number;

  loading = signal(false);
  saving = signal(false);
  errorMessage = signal('');

  departments = signal<DepartmentDTO[]>([]);

  employeeForm = this.fb.nonNullable.group({
    name: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    phone: ['', [Validators.required]],
    hireDate: ['', [Validators.required]],
    salary: [1, [Validators.required, Validators.min(1)]],
    departmentId: [0, [Validators.required, Validators.min(1)]],
  });

  ngOnInit(): void {
    this.loadDepartments();

    const id = this.activatedRoute.snapshot.paramMap.get('id');

    if (id) {
      this.isEditMode.set(true);
      this.employeeId = Number(id);
      this.loadEmployee();
    }
  }

  loadDepartments(): void {
    this.departmentService.getAllDepartments().subscribe({
      next: (response) => {
        this.departments.set(response.data);
      },
      error: (err) => {
        this.errorMessage.set(err.error?.message ?? 'Failed to load departments.');
      },
    });
  }

  loadEmployee(): void {
    this.loading.set(true);
    this.errorMessage.set('');

    this.employeeService.getEmployeeById(this.employeeId).subscribe({
      next: (response) => {
        this.loading.set(false);

        this.employeeForm.patchValue({
          name: response.data.name,
          email: response.data.email,
          phone: response.data.phone,
          hireDate: response.data.hireDate,
          salary: response.data.salary,
          departmentId: response.data.departmentId,
        });
      },
      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.error?.message ?? 'Failed to load employee.');
      },
    });
  }

  saveEmployee(): void {
    if (this.employeeForm.invalid) {
      this.employeeForm.markAllAsTouched();
      return;
    }

    this.saving.set(true);
    this.errorMessage.set('');

    const employee: EmployeeDTO = this.employeeForm.getRawValue();
    employee.hireDate = `${employee.hireDate}T00:00:00`;

    if (this.isEditMode()) {
      this.employeeService.updateEmployee(this.employeeId, employee).subscribe({
        next: () => {
          this.saving.set(false);
          this.router.navigate(['/employees']);
        },
        error: (err) => {
          this.saving.set(false);
          this.errorMessage.set(err.error?.message ?? 'Failed to update employee.');
        },
      });
    } else {
      this.employeeService.createEmployee(employee).subscribe({
        next: () => {
          this.saving.set(false);
          this.router.navigate(['/employees']);
        },
        error: (err) => {
          this.saving.set(false);
          this.errorMessage.set(err.error?.message ?? 'Failed to create employee.');
        },
      });
    }
  }

  get f() {
    return this.employeeForm.controls;
  }
}
