import { ProjectRole } from './project-role.enum';

export interface EmployeeProjectDTO {
  employeeId?: number;
  employeeName?: string;
  projectId?: number;
  projectName?: string;
  role: ProjectRole;
}
