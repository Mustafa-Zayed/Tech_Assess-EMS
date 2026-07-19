package com.ds_middle_east.backend.employee.controller;

import com.ds_middle_east.backend.employee.dto.EmployeeDTO;
import com.ds_middle_east.backend.employee.service.EmployeeService;
import com.ds_middle_east.backend.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee Controller")
@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @Operation(
            summary = "Create Employee",
            description = "create a new employee"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @PostMapping
    public ResponseEntity<Response<EmployeeDTO>> createEmployee(@Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(dto));
    }

    @Operation(
            summary = "Update Employee",
            description = "update an existing employee"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Employee or Department not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Response<EmployeeDTO>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    @Operation(
            summary = "Get Employee by ID",
            description = "get employee by id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Response<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(
            summary = "Get All Employees",
            description = "get all employees"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Response<List<EmployeeDTO>>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(
            summary = "Get Employees by Department",
            description = "get all employees assigned to a specific department"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<Response<List<EmployeeDTO>>> getEmployeesByDepartment(
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(departmentId));
    }

    @Operation(
            summary = "Assign Employee to Department",
            description = "assign an employee to a department"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee has been assigned to department successfully"),
            @ApiResponse(responseCode = "404", description = "Employee or Department not found")
    })
    @PutMapping("{emdId}/department/{departmentId}")
    public ResponseEntity<Response<EmployeeDTO>> assignDepartment(
            @PathVariable Long emdId,
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.assignDepartment(emdId, departmentId));
    }

    @Operation(
            summary = "Delete Employee",
            description = "delete employee by id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee has been deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}