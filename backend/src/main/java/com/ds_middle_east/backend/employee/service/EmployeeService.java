package com.ds_middle_east.backend.employee.service;

import com.ds_middle_east.backend.department.entity.Department;
import com.ds_middle_east.backend.department.repo.DepartmentRepository;
import com.ds_middle_east.backend.employee.dto.EmployeeDTO;
import com.ds_middle_east.backend.employee.entity.Employee;
import com.ds_middle_east.backend.employee.repo.EmployeeRepository;
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
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public Response<EmployeeDTO> createEmployee(EmployeeDTO dto) {
        // check if department exists
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() ->
                        new NotFoundException("Department not found with id: " + dto.getDepartmentId()));

        Employee employee = Employee.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .hireDate(dto.getHireDate())
                .salary(dto.getSalary())
                .department(department)
                .build();

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee has been created successfully");

        EmployeeDTO employeeDTO = modelMapper.map(savedEmployee, EmployeeDTO.class);
        employeeDTO.setDepartmentName(department.getName());
        employeeDTO.setDepartmentId(department.getId());

        return Response.<EmployeeDTO>builder()
                .statusCode(201)
                .data(employeeDTO)
                .message("Employee has been created successfully")
                .build();
    }

    public Response<EmployeeDTO> updateEmployee(Long id, EmployeeDTO dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Employee not found with id: " + id));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() ->
                        new NotFoundException("Department not found with id: " + dto.getDepartmentId()));

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setHireDate(dto.getHireDate());
        employee.setSalary(dto.getSalary());
        employee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee has been updated successfully");

        EmployeeDTO employeeDTO = modelMapper.map(savedEmployee, EmployeeDTO.class);
        employeeDTO.setDepartmentName(department.getName());
        employeeDTO.setDepartmentId(department.getId());

        return Response.<EmployeeDTO>builder()
                .statusCode(200)
                .data(employeeDTO)
                .message("Employee updated successfully")
                .build();
    }

    public Response<EmployeeDTO> getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Employee not found with id: " + id));

        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        employeeDTO.setDepartmentName(employee.getDepartment().getName());
        employeeDTO.setDepartmentId(employee.getDepartment().getId());

        return Response.<EmployeeDTO>builder()
                .statusCode(200)
                .data(employeeDTO)
                .message("Employee retrieved successfully")
                .build();
    }

    public Response<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> dtos = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            EmployeeDTO dto = modelMapper.map(employee, EmployeeDTO.class);
            dto.setDepartmentName(employee.getDepartment().getName());
            dto.setDepartmentId(employee.getDepartment().getId());
            dtos.add(dto);
        }

        return Response.<List<EmployeeDTO>>builder()
                .statusCode(200)
                .data(dtos)
                .message("Employees retrieved successfully")
                .build();
    }

    public Response<List<EmployeeDTO>> getEmployeesByDepartment(Long departmentId) {
        // check if department exists
        if (!departmentRepository.existsById(departmentId)) {
            throw new NotFoundException("Department not found with id: " + departmentId);
        }

        List<EmployeeDTO> dtos = new ArrayList<>();
        List<Employee> employees = employeeRepository.findByDepartmentId((departmentId));

        for (Employee e : employees) {
            EmployeeDTO dto = modelMapper.map(e, EmployeeDTO.class);
            dto.setDepartmentName(e.getDepartment().getName());
            dto.setDepartmentId(e.getDepartment().getId());
            dtos.add(dto);
        }

        return Response.<List<EmployeeDTO>>builder()
                .statusCode(200)
                .data(dtos)
                .message("Employees retrieved successfully")
                .build();
    }

    public Response<EmployeeDTO> assignDepartment(Long employeeId, Long departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new NotFoundException("Employee not found with id: " + employeeId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        new NotFoundException("Department not found with id: " + departmentId));

        employee.setDepartment(department);
        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Employee has been assigned to department successfully");

        EmployeeDTO dto = modelMapper.map(savedEmployee, EmployeeDTO.class);
        dto.setDepartmentName(department.getName());
        dto.setDepartmentId(department.getId());

        return Response.<EmployeeDTO>builder()
                .statusCode(200)
                .data(dto)
                .message("Employee has been assigned to department successfully")
                .build();
    }

    public Response<?> deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Employee not found with id: " + id));

        employeeRepository.delete(employee);
        log.info("Employee has been deleted successfully");

        return Response.builder()
                .statusCode(200)
                .message("Employee has been deleted successfully")
                .build();
    }
}