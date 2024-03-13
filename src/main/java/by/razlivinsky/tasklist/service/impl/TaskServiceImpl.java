package by.razlivinsky.tasklist.service.impl;

import by.razlivinsky.tasklist.domain.exception.ResourceNotFoundException;
import by.razlivinsky.tasklist.domain.task.Status;
import by.razlivinsky.tasklist.domain.task.Task;
import by.razlivinsky.tasklist.repository.TaskRepository;
import by.razlivinsky.tasklist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TaskServiceImpl class provides the implementation for task-related operations.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return the task with the specified ID
     * @throws ResourceNotFoundException if the task with the given ID is not found
     */
    @Override
    @Transactional(readOnly = true)
    public Task getById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found."));
    }

    /**
     * Retrieves all tasks associated with a specific user.
     *
     * @param id the ID of the user whose tasks are to be retrieved
     * @return a list of tasks associated with the given user
     */
    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUserId(Long id) {
        return taskRepository.findAllByUserId(id);
    }

    /**
     * Updates an existing task with the provided information.
     * If the status is not provided, it defaults to 'TODO'.
     *
     * @param task the updated task information
     * @return the updated task
     */
    @Override
    @Transactional
    public Task update(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Status.TODO);
        }
        taskRepository.update(task);
        return task;
    }

    /**
     * Creates a new task with the provided information and associates it with the specified user.
     * The status of the task defaults to 'TODO'.
     *
     * @param task   the task to be created
     * @param userId the ID of the user to whom the task is to be associated
     * @return the created task
     */
    @Override
    @Transactional
    public Task create(Task task, Long userId) {
        task.setStatus(Status.TODO);
        taskRepository.create(task);
        taskRepository.assignToUserById(task.getId(), userId);
        return task;
    }

    /**
     * Deletes a task based on its ID.
     *
     * @param id the ID of the task to be deleted
     */
    @Override
    @Transactional
    public void delete(Long id) {
        taskRepository.delete(id);
    }
}