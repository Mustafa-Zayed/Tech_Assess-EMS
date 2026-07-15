package com.ds_middle_east.backend.project.repo;

import com.ds_middle_east.backend.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
