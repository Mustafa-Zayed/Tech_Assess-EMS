package com.ds_middle_east.backend.project.controller;


import com.ds_middle_east.backend.project.dto.ProjectDTO;
import com.ds_middle_east.backend.response.Response;
import com.ds_middle_east.backend.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Response<ProjectDTO>> createProject(@Valid @RequestBody ProjectDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.createProject(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<ProjectDTO>> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDTO dto) {
        return ResponseEntity.ok(projectService.updateProject(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<ProjectDTO>> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<ProjectDTO>>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.deleteProject(id));
    }
}