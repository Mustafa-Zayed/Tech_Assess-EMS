package com.ds_middle_east.backend.department.controller;

import com.ds_middle_east.backend.department.dto.DepartmentDTO;
import com.ds_middle_east.backend.response.Response;
import com.ds_middle_east.backend.department.service.DepartmentService;
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

@Tag(name = "Department Controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Operation(
            summary = "Create Department",
            description = "create a new department"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Department has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PostMapping
    public ResponseEntity<Response<DepartmentDTO>> createDepartment(@Valid @RequestBody DepartmentDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(departmentService.createDepartment(dto));
    }

    @Operation(
            summary = "Update Department",
            description = "update an existing department"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department has been updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Response<DepartmentDTO>> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentDTO dto) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, dto));
    }

    @Operation(
            summary = "Get Department by id",
            description = "get department by id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Response<DepartmentDTO>> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @Operation(
            summary = "Get All Departments",
            description = "get all departments"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departments retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Response<List<DepartmentDTO>>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @Operation(
            summary = "Delete Department",
            description = "delete department by id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Department has been deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Department cannot be deleted when it has assigned employees"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.deleteDepartment(id));
    }
}
