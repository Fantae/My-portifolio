package com.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.portfolio_backend.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}