package com.ds_middle_east.backend.employee_project.entity;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmployeeProjectId {
    private Long employeeId;

    private Long projectId;
}
