import { Component, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { EmployeeService } from '../../../services/employee.service';
import { EmployeeDTO } from '../../../models/employee.dto';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-employee-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.css',
})
export class EmployeeListComponent {
  private employeeService = inject(EmployeeService);

  employees = signal<EmployeeDTO[]>([]);
  selectedEmployeeForDeletion?: EmployeeDTO;
  private subscriptions: Subscription[] = [];

  loading = signal(false);
  errorMessage = signal('');

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.loading.set(true);
    this.errorMessage.set('');

    const sub = this.employeeService.getAllEmployees().subscribe({
      next: (response) => {
        this.loading.set(false);
        this.employees.set(response.data);
      },

      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.error?.message ?? 'Failed to load employees.');
      },
    });
    this.subscriptions.push(sub);
  }

  deleteEmployee(departmentId: number) {
    const sub = this.employeeService.deleteEmployee(departmentId).subscribe({
      next: (response) => {
        this.loadEmployees();
      },
      error: (err) => {
        this.errorMessage.set(err.error?.message ?? 'Something went wrong while deleting employee');
      },
    });
    this.subscriptions.push(sub);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
