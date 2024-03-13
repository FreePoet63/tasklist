package by.razlivinsky.tasklist.service.impl;

import by.razlivinsky.tasklist.domain.exception.ResourceNotFoundException;
import by.razlivinsky.tasklist.domain.user.Role;
import by.razlivinsky.tasklist.domain.user.User;
import by.razlivinsky.tasklist.repository.UserRepository;
import by.razlivinsky.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

/**
 * UserServiceImpl class provides the implementation for user-related operations.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user with the specified ID
     * @throws ResourceNotFoundException if the user with the given ID is not found
     */
    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return the user with the specified username
     * @throws ResourceNotFoundException if the user with the given username is not found
     */
    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    /**
     * Updates an existing user with the provided information, encoding the password before storage.
     *
     * @param user the updated user information
     * @return the updated user
     */
    @Override
    @Transactional
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return user;
    }

    /**
     * Creates a new user with the provided information, checking for duplicate username and password confirmation.
     * Encodes the password before storage and assigns the 'ROLE_USER' role.
     *
     * @param user the user to be created
     * @return the created user
     * @throws IllegalStateException if the user already exists or if password and password confirmation do not match
     */
    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation do not match");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.create(user);
        Set<Role> roles = Set.of(Role.ROLE_USER);
        userRepository.insertUserRole(user.getId(), Role.ROLE_USER);
        user.setRoles(roles);
        return user;
    }

    /**
     * Checks if a user is the owner of a specific task.
     *
     * @param userId the ID of the user to check
     * @param taskId the ID of the task to check ownership for
     * @return true if the user is the owner of the task, false otherwise
     */
    @Override
    @Transactional
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isTaskOwner(userId, taskId);
    }

    /**
     * Deletes a user based on their ID.
     *
     * @param id the ID of the user to be deleted
     */
    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.delete(id);
    }
}