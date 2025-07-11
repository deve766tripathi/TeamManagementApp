package com.teammangementproject.demo.repository;

import com.teammangementproject.demo.model.Task;
import com.teammangementproject.demo.model.Projects;
import com.teammangementproject.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {

    // 1. Find tasks by assigned user (corrected field name)
    Page<Task> findByAssigned_to_id(User assignedUser, Pageable pageable);

    // 2. Find tasks by assigned user and status (corrected field name)
    Page<Task> findByAssigned_to_idAndStatus(User assignedUser, Task.ETaskStatus status, Pageable pageable);

    // 3. Find tasks by project (with pagination)
    Page<Task> findByProject(Projects project, Pageable pageable);

    // 4. Find tasks by project and status (with pagination)
    Page<Task> findByProjectAndStatus(Projects project, Task.ETaskStatus status, Pageable pageable);

    // 5. Find tasks with due date before or on a specific date (with pagination)
    Page<Task> findByDue_dateLessThanEqual(LocalDate date, Pageable pageable);

    // 6. Get all tasks with pagination
    Page<Task> findAll(Pageable pageable);

    // 7. Find tasks by status (non-paginated)
    List<Task> findByStatus(Task.ETaskStatus status,Pageable pageable);

    // 8. Count tasks by status
    long countByStatus(Task.ETaskStatus status);

    // 9. Count tasks due before or on a specific date
    long countByDue_dateLessThanEqual(LocalDate date);

    // 10. Find all tasks ordered by creation date (for recent tasks)
    List<Task> findAllByOrderByCreated_atDesc();

    Page<Task> findByAssignedToAndStatus(User user, Task.ETaskStatus status, Pageable pageable);
}
