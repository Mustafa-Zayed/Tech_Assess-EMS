package com.ds_middle_east.backend.employee_project.controller;

import com.ds_middle_east.backend.employee_project.dto.EmployeeProjectDTO;
import com.ds_middle_east.backend.response.Response;
import com.ds_middle_east.backend.employee_project.service.EmployeeProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee-projects")
@RequiredArgsConstructor
public class EmployeeProjectController {
    private final EmployeeProjectService employeeProjectService;

    @PostMapping("/employees/{employeeId}/projects/{projectId}")
    public ResponseEntity<Response<EmployeeProjectDTO>> assignEmployeeToProject(
            @PathVariable Long employeeId,
            @PathVariable Long projectId,
            @Valid @RequestBody EmployeeProjectDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeProjectService.assignEmployeeToProject(employeeId, projectId, dto));
    }

    @PutMapping("/employees/{employeeId}/projects/{projectId}")
    public ResponseEntity<Response<EmployeeProjectDTO>> updateEmployeeProject(
            @PathVariable Long employeeId,
            @PathVariable Long projectId,
            @Valid @RequestBody EmployeeProjectDTO dto) {
        return ResponseEntity.ok(employeeProjectService.updateEmployeeProject(employeeId, projectId, dto));
    }

    @GetMapping("/employees/{employeeId}/projects/{projectId}")
    public ResponseEntity<Response<EmployeeProjectDTO>> getEmployeeProject(
            @PathVariable Long employeeId,
            @PathVariable Long projectId) {
        return ResponseEntity.ok(employeeProjectService.getEmployeeProject(employeeId, projectId));
    }

    @GetMapping
    public ResponseEntity<Response<List<EmployeeProjectDTO>>> getAllEmployeeProjects() {
        return ResponseEntity.ok(employeeProjectService.getAllEmployeeProjects());
    }

    @DeleteMapping("/employees/{employeeId}/projects/{projectId}")
    public ResponseEntity<Response<?>> removeEmployeeFromProject(
            @PathVariable Long employeeId,
            @PathVariable Long projectId) {
        return ResponseEntity.ok(employeeProjectService.removeEmployeeFromProject(employeeId, projectId));
    }
}
