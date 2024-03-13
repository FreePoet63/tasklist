package by.razlivinsky.tasklist.repository;

import by.razlivinsky.tasklist.domain.user.Role;
import by.razlivinsky.tasklist.domain.user.User;

import java.util.Optional;

/**
 * UserRepository interface provides methods for accessing and managing user data in the repository.
 *
 * author razlivinsky
 * @since 09.03.2024
 */
public interface UserRepository {
    /**
     * Retrieves the user with the specified ID.
     *
     * @param id the ID of the user
     * @return an Optional containing the user if found, otherwise an empty Optional
     */
    Optional<User> findById(Long id);

    /**
     * Retrieves the user with the specified username.
     *
     * @param username the username of the user
     * @return an Optional containing the user if found, otherwise an empty Optional
     */
    Optional<User> findByUsername(String username);

    /**
     * Updates the user information.
     *
     * @param user the user to be updated
     */
    void update(User user);

    /**
     * Creates a new user.
     *
     * @param user the user to be created
     */
    void create(User user);

    /**
     * Inserts a role for the specified user.
     *
     * @param userId the ID of the user
     * @param role   the role to be assigned
     */
    void insertUserRole(Long userId, Role role);

    /**
     * Checks if the user is the owner of the specified task.
     *
     * @param userId the ID of the user
     * @param taskId the ID of the task
     * @return true if the user is the owner of the task, false otherwise
     */
    boolean isTaskOwner(Long userId, Long taskId);

    /**
     * Deletes the user with the specified ID.
     *
     * @param id the ID of the user to be deleted
     */
    void delete(Long id);
}