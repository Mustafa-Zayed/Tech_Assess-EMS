package com.ds_middle_east.backend.employee_project.entity;

import com.ds_middle_east.backend.empolyee.entity.Employee;
import com.ds_middle_east.backend.project.entity.Project;
import jakarta.persistence.*;
import lombok.*;

/**
 * We use this entity to represent the many-to-many relationship between employees and projects.
 * We could have done without it by using a join table created by JPA, but it has an additional role attribute,
 * so we should create this entity.
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmployeeProject {
    @EmbeddedId
    private EmployeeProjectId id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @Enumerated(EnumType.STRING)
    private ProjectRole role;
}
