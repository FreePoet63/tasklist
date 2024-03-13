package by.razlivinsky.tasklist.service;

import by.razlivinsky.tasklist.domain.task.Task;

import java.util.List;

/**
 * TaskService interface defines methods for interacting with task entities.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
public interface TaskService {
    /**
     * Retrieves a task by its ID.
     *
     * @param id the ID of the task to retrieve
     * @return the task with the specified ID
     */
    Task getById(Long id);


    /**
     * Retrieves all tasks associated with a specific user.
     *
     * @param id the ID of the user whose tasks are to be retrieved
     * @return a list of tasks associated with the given user
     */
    List<Task> getAllByUserId(Long id);

    /**
     * Updates an existing task with the provided information.
     *
     * @param task the updated task information
     * @return the updated task
     */
    Task update(Task task);

    /**
     * Creates a new task with the provided information and associates it with the specified user.
     *
     * @param task   the task to be created
     * @param userId the ID of the user to whom the task is to be associated
     * @return the created task
     */
    Task create(Task task, Long userId);

    /**
     * Deletes a task based on its ID.
     *
     * @param id the ID of the task to be deleted
     */
    void delete(Long id);
}