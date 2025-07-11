package com.teammangementproject.demo.DTO;


import com.teammangementproject.demo.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectDTO {
    private long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private User projectManager;
    private LocalDateTime createdAt;
}