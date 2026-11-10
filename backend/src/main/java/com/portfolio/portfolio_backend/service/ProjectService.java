package com.portfolio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.portfolio.portfolio_backend.model.Project;
import com.portfolio.repository.ProjectRepository;

@Service
public class ProjectService {

    private final ProjectRepository repo;

    public ProjectService(ProjectRepository repo) {
        this.repo = repo;
    }

    public List<Project> getAll() {
        return repo.findAll();
    }

    public Project create(Project project) {
        return repo.save(project);
    }

    public Project update(Long id, Project data) {
        Project p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        p.setTitle(data.getTitle());
        p.setDescription(data.getDescription());
        p.setLink(data.getLink());
        p.setStatus(data.getStatus());
        p.setTechnologies(data.getTechnologies());

        return repo.save(p);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}