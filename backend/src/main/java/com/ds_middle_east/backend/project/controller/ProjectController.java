package com.ds_middle_east.backend.project.controller;

import com.ds_middle_east.backend.project.dto.ProjectDTO;
import com.ds_middle_east.backend.project.service.ProjectService;
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

@Tag(name = "Project Controller")
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @Operation(
            summary = "Create Project",
            description = "create a new project"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Project has been created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @PostMapping
    public ResponseEntity<Response<ProjectDTO>> createProject(@Valid @RequestBody ProjectDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projectService.createProject(dto));
    }

    @Operation(
            summary = "Update Project",
            description = "update an existing project"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project has been updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Project or Department not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Response<ProjectDTO>> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDTO dto) {
        return ResponseEntity.ok(projectService.updateProject(id, dto));
    }

    @Operation(
            summary = "Get Project by ID",
            description = "get project by id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Response<ProjectDTO>> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @Operation(
            summary = "Get All Projects",
            description = "get all projects"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<Response<List<ProjectDTO>>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @Operation(
            summary = "Delete Project",
            description = "delete project by id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project has been deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.deleteProject(id));
    }
}