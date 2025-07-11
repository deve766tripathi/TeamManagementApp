package com.teammangementproject.demo.Service;

import com.teammangementproject.demo.model.Projects;
import com.teammangementproject.demo.model.User;
import com.teammangementproject.demo.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepo projectRepo;

    @Autowired
    public ProjectService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }
    public Projects createProject(Projects project) {
        return projectRepo.save(project);
    }

    // 1. Get all projects with pagination
    public Page<Projects> getAllProjects(Pageable pageable) {
        return projectRepo.findAll(pageable);
    }

    // 2. Get a single project by ID
    public Optional<Projects> getProjectById(Long id) {
        return projectRepo.findById(id);
    }

    // 3. Save or update a project
    public Projects saveOrUpdateProject(Projects project) {
        return projectRepo.save(project);
    }

    // 4. Delete a project by ID
    public void deleteProjectById(Long id) {
        projectRepo.deleteById(id);
    }
    public Page<Projects> getProjectsByManager(User manager, Pageable pageable) {
        return projectRepo.findByProjectManager(manager, pageable);
    }
}
