export interface EmployeeDTO {
  id?: number;
  name: string;
  email: string;
  phone: string;
  hireDate: string;
  salary: number;
  departmentId: number;
  departmentName?: string;
}
