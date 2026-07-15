package com.ds_middle_east.backend.empolyee.repo;

import com.ds_middle_east.backend.empolyee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
