import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { DepartmentDTO } from '../models/department.dto';
import { environment } from '../app';
import { Response } from '../models/response.dto';

@Injectable({
  providedIn: 'root',
})
export class DepartmentService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/departments`;

  createDepartment(department: DepartmentDTO): Observable<Response<DepartmentDTO>> {
    return this.http.post<Response<DepartmentDTO>>(this.apiUrl, department);
  }

  updateDepartment(id: number, department: DepartmentDTO): Observable<Response<DepartmentDTO>> {
    return this.http.put<Response<DepartmentDTO>>(`${this.apiUrl}/${id}`, department);
  }

  getDepartmentById(id: number): Observable<Response<DepartmentDTO>> {
    return this.http.get<Response<DepartmentDTO>>(`${this.apiUrl}/${id}`);
  }

  getAllDepartments(): Observable<Response<DepartmentDTO[]>> {
    return this.http.get<Response<DepartmentDTO[]>>(this.apiUrl);
  }

  deleteDepartment(id: number): Observable<Response<void>> {
    return this.http.delete<Response<void>>(`${this.apiUrl}/${id}`);
  }
}
