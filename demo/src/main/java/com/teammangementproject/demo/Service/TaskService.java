package com.teammangementproject.demo.Service;

import com.teammangementproject.demo.model.Projects;
import com.teammangementproject.demo.model.Task;
import com.teammangementproject.demo.model.User;
import com.teammangementproject.demo.repository.ProjectRepo;
import com.teammangementproject.demo.repository.TaskRepo;
import com.teammangementproject.demo.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class TaskService {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final ProjectRepo projectRepo;

    public Task createTask(Task task, Long projectId, Long assignedTo) {

        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }

        if (task.getDueDate() == null) {
            throw new IllegalArgumentException("Due date must not be null.");
        }

        if (task.getDueDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past.");
        }

        Projects project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + projectId + " not found."));

        if (task.getAssignedTo()) {
            User assignedUser = userRepo.findById(assignedTo)
                    .orElseThrow(() -> new IllegalArgumentException("User with ID " + assignedTo + " not found."));
            task.setAssignedTo(assignedUser);
        } else {
            task.setAssignedTo(null);
        }

        if (task.getStatus() == null) {
            task.setStatus(Task.ETaskStatus.TO_DO);
        }

        if (task.getPriority() == null) {
            task.setPriority(Task.ETaskPriority.MEDIUM);
        }

        task.setProject(project);
        return taskRepo.save(task);
    }

    public Task getTaskById(Long taskId) {
        return taskRepo.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + taskId + " not found."));
    }

    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepo.findAll(pageable);
    }

    public Page<Task> getTasksByAssignedUser(Long userId, Pageable pageable) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found."));
        return taskRepo.findByAssigned_to_id(user, pageable); // ✅ Correct

    }

    public Page<Task> getTasksByProjectAndStatus(Long projectId, Task.ETaskStatus status, Pageable pageable) {
        Projects project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + projectId + " not found."));
        return taskRepo.findByProjectAndStatus(project, status, pageable);
    }

    public Page<Task> getTasksByStatus(Task.ETaskStatus status, Pageable pageable) {
        return (Page<Task>) taskRepo.findByStatus(status, pageable);
    }

    public Page<Task> getTasksByProject(Long projectId, Pageable pageable) {
        Projects project = projectRepo.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + projectId + " not found."));
        return taskRepo.findByProject(project, pageable);
    }

    public Page<Task> getTasksByAssignedUserAndStatus(Long userId, Task.ETaskStatus status, Pageable pageable) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found."));
        return taskRepo.findByAssignedToAndStatus(user, status, pageable);
    }

    public void deleteTask(Long taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + taskId + " not found."));
        taskRepo.delete(task);
    }

    public Task updateTask(Long taskId, Task updatedTask, Long assignedToId, Long projectId) {
        Task existingTask = taskRepo.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + taskId + " not found."));

        if (updatedTask.getTitle() != null) {
            String trimmedTitle = updatedTask.getTitle().trim();
            if (trimmedTitle.isEmpty()) {
                throw new IllegalArgumentException("Task title cannot be empty.");
            }
            existingTask.setTitle(trimmedTitle);
        }

        if (updatedTask.getDueDate() != null) {
            if (updatedTask.getDueDate().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Due date cannot be in the past.");
            }
            existingTask.setDueDate(updatedTask.getDueDate());
        }

        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }

        if (updatedTask.getStatus() != null) {
            existingTask.setStatus(updatedTask.getStatus());
        }

        if (updatedTask.getPriority() != null) {
            existingTask.setPriority(updatedTask.getPriority());
        }

        if (assignedToId != null) {
            User assignedUser = userRepo.findById(assignedToId)
                    .orElseThrow(() -> new IllegalArgumentException("User with ID " + assignedToId + " not found."));
            existingTask.setAssignedTo(assignedUser);
        }

        if (projectId != null) {
            Projects project = projectRepo.findById(projectId)
                    .orElseThrow(() -> new IllegalArgumentException("Project with ID " + projectId + " not found."));
            existingTask.setProject(project);
        }

        return taskRepo.save(existingTask);
    }
}
