import { Component, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';

import { EmployeeProjectService } from '../../../services/employee-project.service';
import { EmployeeProjectDTO } from '../../../models/employee_project.dto';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-employee-projects-list',
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './employee-projects-list.component.html',
  styleUrls: ['./employee-projects-list.component.css'],
})
export class EmployeeProjectsListComponent {
  private readonly employeeProjectService = inject(EmployeeProjectService);

  employeeProjects = signal<EmployeeProjectDTO[]>([]);
  loading = signal(false);
  errorMessage = signal('');

  selectedEmployeeProjectForDeletion: EmployeeProjectDTO | null = null;

  private subscriptions = new Subscription();

  searchTerm = signal('');

  filteredEmployeeProjects = computed(() => {
    const term = this.searchTerm().trim().toLowerCase();

    if (!term) {
      return this.employeeProjects();
    }

    return this.employeeProjects().filter(
      (employeeProject) =>
        employeeProject.employeeName?.toLowerCase().includes(term) ||
        employeeProject.projectName?.toLowerCase().includes(term) ||
        employeeProject.role.toLowerCase().includes(term),
    );
  });

  ngOnInit(): void {
    this.loadEmployeeProjects();
  }

  loadEmployeeProjects(): void {
    this.loading.set(true);
    this.errorMessage.set('');

    const sub = this.employeeProjectService.getAllEmployeeProjects().subscribe({
      next: (response) => {
        this.employeeProjects.set(response.data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error(err);
        this.errorMessage.set('Failed to load employee projects.');
        this.loading.set(false);
      },
    });
    this.subscriptions.add(sub);
  }

  removeEmployeeFromProject(employeeId: number, projectId: number): void {
    const sub = this.employeeProjectService
      .removeEmployeeFromProject(employeeId, projectId)
      .subscribe({
        next: () => {
          this.loadEmployeeProjects();
        },
        error: (err) => {
          this.errorMessage.set('Failed to delete employee assignment.');
        },
      });

    this.subscriptions.add(sub);
  }

  ngOnDestroy(): void {
    this.subscriptions.unsubscribe();
  }
}
