import { Component, computed, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';

import { ProjectService } from '../../../services/project.service';
import { ProjectDTO } from '../../../models/project.dto';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-project-list',
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './project-list.component.html',
  styleUrl: './project-list.component.css',
})
export class ProjectListComponent {
  private projectService = inject(ProjectService);

  projects = signal<ProjectDTO[]>([]);
  selectedProjectForDeletion?: ProjectDTO;
  private subscriptions: Subscription[] = [];

  loading = signal(false);
  errorMessage = signal('');

  searchTerm = signal('');

  filteredProjects = computed(() => {
    const term = this.searchTerm().trim().toLowerCase();

    if (!term) {
      return this.projects();
    }

    return this.projects().filter(
      (project) =>
        project.name.toLowerCase().includes(term) ||
        project.description.toLowerCase().includes(term) ||
        project.startDate.toLowerCase().includes(term) ||
        project.endDate.toLowerCase().includes(term) ||
        project.departmentName?.toLowerCase().includes(term),
    );
  });

  ngOnInit(): void {
    this.loadProjects();
  }

  loadProjects(): void {
    this.loading.set(true);
    this.errorMessage.set('');

    const sub = this.projectService.getAllProjects().subscribe({
      next: (response) => {
        this.loading.set(false);
        this.projects.set(response.data);
      },
      error: (err) => {
        this.loading.set(false);
        this.errorMessage.set(err.error?.message ?? 'Failed to load projects.');
      },
    });
    this.subscriptions.push(sub);
  }

  deleteProject(projectId: number): void {
    const sub = this.projectService.deleteProject(projectId).subscribe({
      next: () => {
        this.loadProjects();
      },
      error: (err) => {
        this.errorMessage.set(err.error?.message ?? 'Something went wrong while deleting project.');
      },
    });
    this.subscriptions.push(sub);
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }
}
