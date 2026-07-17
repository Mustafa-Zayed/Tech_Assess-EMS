import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../app';
import { Response } from '../models/response.dto';
import { ProjectDTO } from '../models/project.dto';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/projects`;

  createProject(project: ProjectDTO): Observable<Response<ProjectDTO>> {
    return this.http.post<Response<ProjectDTO>>(this.apiUrl, project);
  }

  updateProject(id: number, project: ProjectDTO): Observable<Response<ProjectDTO>> {
    return this.http.put<Response<ProjectDTO>>(`${this.apiUrl}/${id}`, project);
  }

  getProjectById(id: number): Observable<Response<ProjectDTO>> {
    return this.http.get<Response<ProjectDTO>>(`${this.apiUrl}/${id}`);
  }

  getAllProjects(): Observable<Response<ProjectDTO[]>> {
    return this.http.get<Response<ProjectDTO[]>>(this.apiUrl);
  }

  deleteProject(id: number): Observable<Response<void>> {
    return this.http.delete<Response<void>>(`${this.apiUrl}/${id}`);
  }
}
