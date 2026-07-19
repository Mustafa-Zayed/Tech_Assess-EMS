package com.ds_middle_east.backend.employee_project.controller;

import com.ds_middle_east.backend.employee_project.dto.EmployeeProjectDTO;
import com.ds_middle_east.backend.employee_project.service.EmployeeProjectService;
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

@Tag(name = "Employee Project Controller")
@RestController
@RequestMapping("/api/v1/employee-projects")
@RequiredArgsConstructor
public class EmployeeProjectController {
    private final EmployeeProjectService employeeProjectService;

    @Operation(
            summary = "Assign Employee to Project",
            description = "assign an employee to a project"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee assigned to project successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "400", description = "Employee is already assigned to this project"),
            @ApiResponse(responseCode = "404", description = "Employee or Project not found")
    })
    @PostMapping("/employees/{employeeId}/projects/{projectId}")
    public ResponseEntity<Response<EmployeeProjectDTO>> assignEmployeeToProject(
            @PathVariable Long employeeId,
            @PathVariable Long projectId,
            @Valid @RequestBody EmployeeProjectDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeProjectService.assignEmployeeToProject(employeeId, projectId, dto));
    }

    @Operation(
            summary = "Update Employee Project",
            description = "update employee project assignment"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee project updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Employee project assignment not found")
    })
    @PutMapping("/employees/{employeeId}/projects/{projectId}")
    public ResponseEntity<Response<EmployeeProjectDTO>> updateEmployeeProject(
            @PathVariable Long employeeId,
            @PathVariable Long projectId,
            @Valid @RequestBody EmployeeProjectDTO dto) {
        return ResponseEntity.ok(employeeProjectService.updateEmployeeProject(employeeId, projectId, dto));
    }

    @Operation(
            summary = "Get Employee Project",
            description = "get employee project assignment"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee project retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Employee project assignment not found")
    })
    @GetMapping("/employees/{employeeId}/projects/{projectId}")
    public ResponseEntity<Response<EmployeeProjectDTO>> getEmployeeProject(
            @PathVariable Long employeeId,
            @PathVariable Long projectId) {
        return ResponseEntity.ok(employeeProjectService.getEmployeeProject(employeeId, projectId));
    }

    @Operation(
            summary = "Get All Employee Projects",
            description = "get all employee project assignments"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee projects have been retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Response<List<EmployeeProjectDTO>>> getAllEmployeeProjects() {
        return ResponseEntity.ok(employeeProjectService.getAllEmployeeProjects());
    }

    @Operation(
            summary = "Remove Employee from Project",
            description = "remove an employee from a project"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee removed from project successfully"),
            @ApiResponse(responseCode = "404", description = "Employee project assignment not found")
    })
    @DeleteMapping("/employees/{employeeId}/projects/{projectId}")
    public ResponseEntity<Response<?>> removeEmployeeFromProject(
            @PathVariable Long employeeId,
            @PathVariable Long projectId) {
        return ResponseEntity.ok(employeeProjectService.removeEmployeeFromProject(employeeId, projectId));
    }
}