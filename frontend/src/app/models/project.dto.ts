export interface ProjectDTO {
  id?: number;
  name: string;
  description: string;
  startDate: string;
  endDate: string;
  departmentId: number;
  departmentName?: string;
}
