package com.ds_middle_east.backend.department.service;

import com.ds_middle_east.backend.department.dto.DepartmentDTO;
import com.ds_middle_east.backend.department.entity.Department;
import com.ds_middle_east.backend.department.repo.DepartmentRepository;
import com.ds_middle_east.backend.employee.repo.EmployeeRepository;
import com.ds_middle_east.backend.exception.BusinessException;
import com.ds_middle_east.backend.exception.NotFoundException;
import com.ds_middle_east.backend.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public Response<DepartmentDTO> createDepartment(DepartmentDTO dto) {
        Department department = Department.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .budget(dto.getBudget())
                .build();

        Department savedDepartment = departmentRepository.save(department);
        log.info("Department has been created successfully");

        DepartmentDTO map = modelMapper.map(savedDepartment, DepartmentDTO.class);

        return Response.<DepartmentDTO>builder()
                .statusCode(201)
                .data(map)
                .message("Department has been created successfully")
                .build();
    }

    public Response<DepartmentDTO> updateDepartment(Long id, DepartmentDTO dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Department not found with id: " + id));

        department.setName(dto.getName());
        department.setLocation(dto.getLocation());
        department.setBudget(dto.getBudget());

        Department savedDepartment = departmentRepository.save(department);

        DepartmentDTO map = modelMapper.map(savedDepartment, DepartmentDTO.class);

        return Response.<DepartmentDTO>builder()
                .statusCode(200)
                .data(map)
                .message("Department has been created successfully")
                .build();
    }

    public Response<DepartmentDTO> getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Department not found with id: " + id));

        DepartmentDTO map = modelMapper.map(department, DepartmentDTO.class);

        return Response.<DepartmentDTO>builder()
                .statusCode(200)
                .data(map)
                .message("Department retrieved successfully")
                .build();
    }

    public Response<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> dtos= new ArrayList<>();
        List<Department> departments = departmentRepository.findAll();

        for (Department department : departments) {
            dtos.add(modelMapper.map(department, DepartmentDTO.class));
        }
        return Response.<List<DepartmentDTO>>builder()
                .statusCode(200)
                .data(dtos)
                .message("Departments retrieved successfully")
                .build();
    }

    public Response<?> deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Department not found with id: " + id));

        // check if the department has assigned employees.
        if (employeeRepository.existsByDepartmentId(id)) { // We can use department.employees.isEmpty() instead
            throw new BusinessException(
                    "Department cannot be deleted when it has assigned employees");
        }

        departmentRepository.delete(department);
        log.info("Department has been deleted successfully");

        return Response.builder()
                .statusCode(200)
                .message("Department has been deleted successfully")
                .build();
    }
}
