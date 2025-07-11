package com.teammangementproject.demo.repository;

import com.teammangementproject.demo.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(Roles.ERole name);
}