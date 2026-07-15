package com.ds_middle_east.backend.project.service;

import com.ds_middle_east.backend.exception.NotFoundException;
import com.ds_middle_east.backend.project.entity.Project;
import com.ds_middle_east.backend.department.entity.Department;
import com.ds_middle_east.backend.project.dto.ProjectDTO;
import com.ds_middle_east.backend.project.repo.ProjectRepository;
import com.ds_middle_east.backend.department.repo.DepartmentRepository;
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
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public Response<ProjectDTO> createProject(ProjectDTO dto) {
        // Check if department exists
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() ->
                        new NotFoundException("Department not found with id: " + dto.getDepartmentId()));

        Project project = Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .department(department)
                .build();

        Project savedProject = projectRepository.save(project);
        log.info("Project has been created successfully");

        ProjectDTO projectDTO = modelMapper.map(savedProject, ProjectDTO.class);
        projectDTO.setDepartmentName(department.getName());
        projectDTO.setDepartmentId(department.getId());

        return Response.<ProjectDTO>builder()
                .statusCode(201)
                .message("Project has been created successfully")
                .data(projectDTO)
                .build();
    }

    public Response<ProjectDTO> updateProject(Long id, ProjectDTO dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Project not found with id: " + id));

        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() ->
                        new NotFoundException("Department not found with id: " + dto.getDepartmentId()));

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setDepartment(department);

        Project savedProject = projectRepository.save(project);
        log.info("Project has been updated successfully");

        ProjectDTO projectDTO = modelMapper.map(savedProject, ProjectDTO.class);
        projectDTO.setDepartmentName(department.getName());
        projectDTO.setDepartmentId(department.getId());

        return Response.<ProjectDTO>builder()
                .statusCode(200)
                .message("Project has been updated successfully")
                .data(projectDTO)
                .build();
    }

    public Response<ProjectDTO> getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Project not found with id: " + id));

        ProjectDTO projectDTO = modelMapper.map(project, ProjectDTO.class);
        projectDTO.setDepartmentName(project.getDepartment().getName());
        projectDTO.setDepartmentId(project.getDepartment().getId());

        return Response.<ProjectDTO>builder()
                .statusCode(200)
                .message("Project retrieved successfully")
                .data(projectDTO)
                .build();
    }

    public Response<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> dtos = new ArrayList<>();
        List<Project> projects = projectRepository.findAll();

        for (Project project : projects) {
            ProjectDTO dto = modelMapper.map(project, ProjectDTO.class);
            dto.setDepartmentName(project.getDepartment().getName());
            dto.setDepartmentId(project.getDepartment().getId());
            dtos.add(dto);
        }

        return Response.<List<ProjectDTO>>builder()
                .statusCode(200)
                .message("Projects retrieved successfully")
                .data(dtos)
                .build();
    }

    public Response<?> deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Project not found with id: " + id));

        projectRepository.delete(project);

        return Response.builder()
                .statusCode(200)
                .message("Project has been deleted successfully")
                .build();
    }
}