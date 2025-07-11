package com.teammangementproject.demo.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition ="TEXT",nullable = false)
    private String description;

    @Column(name = "start_date", updatable = false)
    private LocalDateTime start_date;

    @Column(name = "end_date", updatable = false)
    private LocalDateTime end_date;

    @ManyToOne
    @JoinColumn(name = "project_manager_id", nullable = false)
    private User project_manager;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime created_at;
}