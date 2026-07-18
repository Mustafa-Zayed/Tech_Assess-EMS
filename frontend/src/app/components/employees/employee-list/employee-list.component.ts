import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import { EmployeeService } from '../../../services/employee.service';
import { EmployeeDTO } from '../../../models/employee.dto';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-employee-list',
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './employee-list.component.html',
  styleUrl: './employee-list.component.css',
})
export class EmployeeListComponent implements OnInit {
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

    this.employeeService.getAllEmployees().subscribe({
      next: (response) => {
        this.loading.set(false);
        this.employees.set(response.data);
      },

      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.error?.message ?? 'Failed to load employees.');
      },
    });
  }

  deleteEmployee(departmentId: number) {
    this.employeeService.deleteEmployee(departmentId).subscribe({
      next: (response) => {
        this.loadEmployees();
      },
      error: (err) => {
        this.errorMessage.set(err.error?.message ?? 'Something went wrong while deleting employee');
      },
    });
  }
}
