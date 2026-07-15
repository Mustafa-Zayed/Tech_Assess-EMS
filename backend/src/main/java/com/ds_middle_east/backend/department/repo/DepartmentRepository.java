package com.ds_middle_east.backend.department.repo;

import com.ds_middle_east.backend.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
