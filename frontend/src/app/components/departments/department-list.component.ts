import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DepartmentService } from '../../services/department.service';
import { DepartmentDTO } from '../../models/department.dto';
import { Response } from '../../models/response.dto';
import { Subscription } from 'rxjs';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-department-list',
  imports: [CommonModule, RouterLink],
  templateUrl: './department-list.component.html',
})
export class DepartmentListComponent implements OnInit {
  private departmentService = inject(DepartmentService);

  departments = signal<DepartmentDTO[]>([]);
  selectedDepartmentForDeletion?: DepartmentDTO;
  private subscriptions: Subscription[] = [];

  loading = signal<boolean>(false);
  errorMessage = signal<string>('');

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

        this.errorMessage.set(
          err.error?.message ?? 'Something went wrong while loading departments',
        );
      },
    });

    this.subscriptions.push(sub);
  }

  deleteDepartment(departmentId: number) {
    this.departmentService.deleteDepartment(departmentId).subscribe({
      next: (response: Response<void>) => {
        this.loadDepartments();
      },
      error: (err) => {
        this.errorMessage.set(
          err.error?.message ?? 'Something went wrong while deleting department',
        );
      },
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
