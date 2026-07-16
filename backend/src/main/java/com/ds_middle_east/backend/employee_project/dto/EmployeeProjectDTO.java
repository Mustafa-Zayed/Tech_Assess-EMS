package com.ds_middle_east.backend.employee_project.dto;

import com.ds_middle_east.backend.employee_project.entity.ProjectRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeProjectDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long employeeId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String employeeName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long projectId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String projectName;

    @NotNull(message = "Role is required")
    private ProjectRole role;
}