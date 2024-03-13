package by.razlivinsky.tasklist.service;

import by.razlivinsky.tasklist.domain.user.User;

/**
 * UserService interface defines methods for interacting with user entities.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
public interface UserService {
    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID
     */
    User getById(Long id);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return the user with the specified username
     */
    User getByUsername(String username);

    /**
     * Updates an existing user with the provided information.
     *
     * @param user the updated user information
     * @return the updated user
     */
    User update(User user);

    /**
     * Creates a new user with the provided information.
     *
     * @param user the user to be created
     * @return the created user
     */
    User create(User user);

    /**
     * Checks if a user is the owner of a specific task.
     *
     * @param userId the ID of the user to check
     * @param taskId the ID of the task to check
     * @return true if the user is the owner of the task, otherwise false
     */
    boolean isTaskOwner(Long userId, Long taskId);

    /**
     * Deletes a user based on their ID.
     *
     * @param id the ID of the user to be deleted
     */
    void delete(Long id);
}