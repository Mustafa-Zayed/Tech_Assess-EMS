import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../app';
import { Response } from '../models/response.dto';
import { EmployeeProjectDTO } from '../models/employee_project.dto';

@Injectable({
  providedIn: 'root',
})
export class EmployeeProjectService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/employee-projects`;

  assignEmployeeToProject(
    employeeId: number,
    projectId: number,
    employeeProject: EmployeeProjectDTO,
  ): Observable<Response<EmployeeProjectDTO>> {
    return this.http.post<Response<EmployeeProjectDTO>>(
      `${this.apiUrl}/employees/${employeeId}/projects/${projectId}`,
      employeeProject,
    );
  }

  updateEmployeeProject(
    employeeId: number,
    projectId: number,
    employeeProject: EmployeeProjectDTO,
  ): Observable<Response<EmployeeProjectDTO>> {
    return this.http.put<Response<EmployeeProjectDTO>>(
      `${this.apiUrl}/employees/${employeeId}/projects/${projectId}`,
      employeeProject,
    );
  }

  getEmployeeProject(
    employeeId: number,
    projectId: number,
  ): Observable<Response<EmployeeProjectDTO>> {
    return this.http.get<Response<EmployeeProjectDTO>>(
      `${this.apiUrl}/employees/${employeeId}/projects/${projectId}`,
    );
  }

  getAllEmployeeProjects(): Observable<Response<EmployeeProjectDTO[]>> {
    return this.http.get<Response<EmployeeProjectDTO[]>>(this.apiUrl);
  }

  removeEmployeeFromProject(employeeId: number, projectId: number): Observable<Response<void>> {
    return this.http.delete<Response<void>>(
      `${this.apiUrl}/employees/${employeeId}/projects/${projectId}`,
    );
  }
}
