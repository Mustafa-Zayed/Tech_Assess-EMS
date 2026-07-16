package com.ds_middle_east.backend.department.controller;

import com.ds_middle_east.backend.department.dto.DepartmentDTO;
import com.ds_middle_east.backend.response.Response;
import com.ds_middle_east.backend.department.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<Response<DepartmentDTO>> createDepartment(@Valid @RequestBody DepartmentDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(departmentService.createDepartment(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<DepartmentDTO>> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentDTO dto) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<DepartmentDTO>> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<DepartmentDTO>>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.deleteDepartment(id));
    }
}
