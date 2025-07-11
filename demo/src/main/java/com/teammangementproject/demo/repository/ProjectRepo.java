package com.teammangementproject.demo.repository;

import com.teammangementproject.demo.model.Projects;
import com.teammangementproject.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepo extends JpaRepository<Projects, Long> {

    Page<Projects> findByProjectManager(User projectManager, Pageable pageable);

    Page<Projects> findAll(Pageable pageable);

    long countByEndDateIsNullOrEndDateAfter(LocalDate today);

    List<Projects> findAllByOrderByCreatedAtDesc();
}