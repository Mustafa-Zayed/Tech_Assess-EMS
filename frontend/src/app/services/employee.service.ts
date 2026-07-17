import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../app';
import { Response } from '../models/response.dto';
import { EmployeeDTO } from '../models/employee.dto';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/employees`;

  createEmployee(employee: EmployeeDTO): Observable<Response<EmployeeDTO>> {
    return this.http.post<Response<EmployeeDTO>>(this.apiUrl, employee);
  }

  updateEmployee(id: number, employee: EmployeeDTO): Observable<Response<EmployeeDTO>> {
    return this.http.put<Response<EmployeeDTO>>(`${this.apiUrl}/${id}`, employee);
  }

  getEmployeeById(id: number): Observable<Response<EmployeeDTO>> {
    return this.http.get<Response<EmployeeDTO>>(`${this.apiUrl}/${id}`);
  }

  getAllEmployees(): Observable<Response<EmployeeDTO[]>> {
    return this.http.get<Response<EmployeeDTO[]>>(this.apiUrl);
  }

  getEmployeesByDepartment(departmentId: number): Observable<Response<EmployeeDTO[]>> {
    return this.http.get<Response<EmployeeDTO[]>>(`${this.apiUrl}/department/${departmentId}`);
  }

  assignDepartment(employeeId: number, departmentId: number): Observable<Response<EmployeeDTO>> {
    return this.http.put<Response<EmployeeDTO>>(
      `${this.apiUrl}/${employeeId}/department/${departmentId}`,
      {},
    );
  }

  deleteEmployee(id: number): Observable<Response<void>> {
    return this.http.delete<Response<void>>(`${this.apiUrl}/${id}`);
  }
}
