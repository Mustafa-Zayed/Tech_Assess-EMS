package com.ds_middle_east.backend.employee.controller;

import com.ds_middle_east.backend.employee.dto.EmployeeDTO;
import com.ds_middle_east.backend.employee.service.EmployeeService;
import com.ds_middle_east.backend.response.Response;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Response<EmployeeDTO>> createEmployee(@Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeService.createEmployee(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<EmployeeDTO>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<EmployeeDTO>>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<Response<List<EmployeeDTO>>> getEmployeesByDepartment(
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(departmentId));
    }

    @PutMapping("{emdId}/department/{departmentId}")
    public ResponseEntity<Response<EmployeeDTO>> assignDepartment(
            @PathVariable Long emdId,
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.assignDepartment(emdId, departmentId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}