package com.teammangementproject.demo.DTO;


import com.teammangementproject.demo.model.Projects;
import com.teammangementproject.demo.model.Task;
import com.teammangementproject.demo.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {
    private long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Projects project;
    private User assignedTo;
    private LocalDateTime createdAt;
}
