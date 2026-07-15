package com.ds_middle_east.backend.employee.repo;

import com.ds_middle_east.backend.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByDepartmentId(Long id);

    List<Employee> findByDepartmentId(Long departmentId);
}
