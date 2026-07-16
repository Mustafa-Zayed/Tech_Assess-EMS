package com.ds_middle_east.backend.employee_project.service;

import com.ds_middle_east.backend.exception.BusinessException;
import com.ds_middle_east.backend.exception.NotFoundException;

import com.ds_middle_east.backend.employee_project.dto.EmployeeProjectDTO;
import com.ds_middle_east.backend.employee_project.entity.EmployeeProject;
import com.ds_middle_east.backend.employee_project.entity.EmployeeProjectId;
import com.ds_middle_east.backend.employee_project.repo.EmployeeProjectRepository;
import com.ds_middle_east.backend.employee.entity.Employee;
import com.ds_middle_east.backend.employee.repo.EmployeeRepository;
import com.ds_middle_east.backend.project.entity.Project;
import com.ds_middle_east.backend.project.repo.ProjectRepository;
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
public class EmployeeProjectService {

    private final EmployeeProjectRepository employeeProjectRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;

    /**
     * The endpoint will contain the IDs, for example: /employees/{employeeId}/projects/{projectId}
     * the request body only needs the role
     */
    public Response<EmployeeProjectDTO> assignEmployeeToProject(
            Long employeeId,
            Long projectId,
            EmployeeProjectDTO dto) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new NotFoundException("Employee not found with id: " + employeeId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException("Project not found with id: " + projectId));

        EmployeeProjectId id = new EmployeeProjectId(employeeId, projectId);

        // check if the employee is already assigned to this project
        if (employeeProjectRepository.existsById(id)) {
            throw new BusinessException("Employee is already assigned to this project.");
        }

        EmployeeProject employeeProject = EmployeeProject.builder()
                .id(id)
                .employee(employee)
                .project(project)
                .role(dto.getRole())
                .build();

        EmployeeProject savedEmployeeProject = employeeProjectRepository.save(employeeProject);
        log.info("Employee {} has been assigned to project {}", employee.getName(), project.getName());

        EmployeeProjectDTO employeeProjectDTO = modelMapper.map(savedEmployeeProject, EmployeeProjectDTO.class);
        employeeProjectDTO.setEmployeeId(employeeId);
        employeeProjectDTO.setEmployeeName(employee.getName());
        employeeProjectDTO.setProjectId(projectId);
        employeeProjectDTO.setProjectName(project.getName());

        return Response.<EmployeeProjectDTO>builder()
                .statusCode(200)
                .message("Employee assigned to project successfully")
                .data(employeeProjectDTO)
                .build();
    }

    /**
     * The user can only update the role of an employee in a project
     */
    public Response<EmployeeProjectDTO> updateEmployeeProject(
            Long employeeId,
            Long projectId,
            EmployeeProjectDTO dto) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new NotFoundException("Employee not found with id: " + employeeId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException("Project not found with id: " + projectId));

        EmployeeProjectId id = new EmployeeProjectId(employeeId, projectId);

        EmployeeProject employeeProject = employeeProjectRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Employee project assignment not found."));

        employeeProject.setRole(dto.getRole());
        EmployeeProject updatedEmployeeProject = employeeProjectRepository.save(employeeProject);
        log.info("Employee {} has been updated to project {}", employee.getName(), project.getName());

        EmployeeProjectDTO employeeProjectDTO = modelMapper.map(updatedEmployeeProject, EmployeeProjectDTO.class);
        employeeProjectDTO.setEmployeeId(employeeId);
        employeeProjectDTO.setEmployeeName(employee.getName());
        employeeProjectDTO.setProjectId(projectId);
        employeeProjectDTO.setProjectName(project.getName());

        return Response.<EmployeeProjectDTO>builder()
                .statusCode(200)
                .message("Employee project updated successfully")
                .data(employeeProjectDTO)
                .build();
    }

    public Response<EmployeeProjectDTO> getEmployeeProject(
            Long employeeId,
            Long projectId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new NotFoundException("Employee not found with id: " + employeeId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new NotFoundException("Project not found with id: " + projectId));

        EmployeeProjectId id = new EmployeeProjectId(employeeId, projectId);

        EmployeeProject employeeProject = employeeProjectRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Employee project assignment not found"));

        EmployeeProjectDTO employeeProjectDTO = modelMapper.map(employeeProject, EmployeeProjectDTO.class);
        employeeProjectDTO.setEmployeeId(employeeId);
        employeeProjectDTO.setEmployeeName(employee.getName());
        employeeProjectDTO.setProjectId(projectId);
        employeeProjectDTO.setProjectName(project.getName());

        return Response.<EmployeeProjectDTO>builder()
                .statusCode(200)
                .message("Employee project retrieved successfully")
                .data(employeeProjectDTO)
                .build();
    }

    public Response<List<EmployeeProjectDTO>> getAllEmployeeProjects() {
        List<EmployeeProjectDTO> dtos = new ArrayList<>();
        List<EmployeeProject> employeeProjects = employeeProjectRepository.findAll();

        for (EmployeeProject employeeProject : employeeProjects) {
            EmployeeProjectDTO dto = modelMapper.map(employeeProject, EmployeeProjectDTO.class);
            dto.setEmployeeId(employeeProject.getEmployee().getId());
            dto.setEmployeeName(employeeProject.getEmployee().getName());
            dto.setProjectId(employeeProject.getProject().getId());
            dto.setProjectName(employeeProject.getProject().getName());
            dtos.add(dto);
        }

        return Response.<List<EmployeeProjectDTO>>builder()
                .statusCode(200)
                .message("Employee projects have been retrieved successfully")
                .data(dtos)
                .build();
    }

    public Response<?> removeEmployeeFromProject(
            Long employeeId,
            Long projectId) {

        EmployeeProjectId id = new EmployeeProjectId(employeeId, projectId);

        EmployeeProject employeeProject = employeeProjectRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Employee project assignment not found"));

        employeeProjectRepository.delete(employeeProject);

        return Response.builder()
                .statusCode(200)
                .message("Employee removed from project successfully")
                .build();
    }
}
