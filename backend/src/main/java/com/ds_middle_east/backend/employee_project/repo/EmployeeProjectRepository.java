package com.ds_middle_east.backend.employee_project.repo;

import com.ds_middle_east.backend.employee_project.entity.EmployeeProject;
import com.ds_middle_east.backend.employee_project.entity.EmployeeProjectId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, EmployeeProjectId> {
}
