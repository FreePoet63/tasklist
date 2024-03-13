package by.razlivinsky.tasklist.repository;

import by.razlivinsky.tasklist.domain.task.Task;

import java.util.List;
import java.util.Optional;

/**
 * TaskRepository interface provides methods for accessing and managing task data.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
public interface TaskRepository {
    /**
     * Retrieves the task with the specified ID.
     *
     * @param id the ID of the task
     * @return an Optional containing the task if found, otherwise an empty Optional
     */
    Optional<Task> findById(Long id);

    /**
     * Retrieves all tasks associated with the specified user ID.
     *
     * @param userId the ID of the user
     * @return a list of tasks associated with the user
     */
    List<Task> findAllByUserId(Long userId);

    /**
     * Assigns a task to the specified user by their ID.
     *
     * @param taskId the ID of the task
     * @param userId the ID of the user to whom the task is to be assigned
     */
    void assignToUserById(Long taskId, Long userId);

    /**
     * Updates the task information.
     *
     * @param task the task to be updated
     */
    void update(Task task);

    /**
     * Creates a new task.
     *
     * @param task the task to be created
     */
    void create(Task task);

    /**
     * Deletes the task with the specified ID.
     *
     * @param id the ID of the task to be deleted
     */
    void delete(Long id);
}