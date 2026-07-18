import { Component, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DepartmentService } from '../../../services/department.service';
import { DepartmentDTO } from '../../../models/department.dto';
import { Response } from '../../../models/response.dto';
import { Subscription } from 'rxjs';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-department-list',
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './department-list.component.html',
  styleUrls: ['./department-list.component.css'],
})
export class DepartmentListComponent {
  private departmentService = inject(DepartmentService);

  departments = signal<DepartmentDTO[]>([]);
  selectedDepartmentForDeletion?: DepartmentDTO;
  private subscriptions: Subscription[] = [];

  loading = signal<boolean>(false);
  errorMessage = signal<string>('');

  searchTerm = signal('');

  filteredDepartments = computed(() => {
    const term = this.searchTerm().trim().toLowerCase();

    // return all departments, if search term is empty
    if (!term) {
      return this.departments();
    }

    return this.departments().filter(
      (department) =>
        department.name.toLowerCase().includes(term) ||
        department.location.toLowerCase().includes(term) ||
        department.budget.toString().toLowerCase().includes(term),
    );
  });

  ngOnInit(): void {
    this.loadDepartments();
  }

  loadDepartments(): void {
    this.loading.set(true);
    this.errorMessage.set('');

    const sub = this.departmentService.getAllDepartments().subscribe({
      next: (response: Response<DepartmentDTO[]>) => {
        this.loading.set(false);

        if (response.data && response.data.length > 0) {
          this.departments.set(response.data);
        } else {
          this.errorMessage.set(response.message);
        }
      },
      error: (err) => {
        this.loading.set(false);

        this.errorMessage.set(err.error?.message ?? 'Failed to load departments.');
      },
    });

    this.subscriptions.push(sub);
  }

  deleteDepartment(departmentId: number) {
    const sub = this.departmentService.deleteDepartment(departmentId).subscribe({
      next: (response: Response<void>) => {
        this.loadDepartments();
      },
      error: (err) => {
        this.errorMessage.set(
          err.error?.message ?? 'Something went wrong while deleting department',
        );
      },
    });
    this.subscriptions.push(sub);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
