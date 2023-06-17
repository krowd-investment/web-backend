package com.swd392.funfundbe.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.swd392.funfundbe.model.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    @Query("select p from Project p")
    public ArrayList<Project> getAllProjects();

    public Project findByProjectId(int projectId);
}
