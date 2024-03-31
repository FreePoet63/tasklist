package by.razlivinsky.tasklist.repository.impl;

import by.razlivinsky.tasklist.domain.exception.ResourceMappingException;
import by.razlivinsky.tasklist.domain.user.Role;
import by.razlivinsky.tasklist.domain.user.User;
import by.razlivinsky.tasklist.repository.DataSourceConfig;
import by.razlivinsky.tasklist.repository.UserRepository;
import by.razlivinsky.tasklist.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * UserRepositoryImpl class provides an implementation of UserRepository interface for interacting with the database.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT u.id AS user_id,
                   u.name AS user_name,
            	   u.username AS user_username,
            	   u.password AS user_password,
            	   ur.role AS user_role_role,
            	   t.id AS task_id,
            	   t.title AS task_title,
            	   t.description AS task_description,
            	   t.expiration_date AS task_expiration_date,
                   t.status AS task_status
            FROM users u
                   LEFT JOIN users_roles ur ON u.id = ur.user_id
            	   LEFT JOIN users_tasks ut ON u.id = ut.user_id
            	   LEFT JOIN tasks t ON ut.task_id = t.id
            WHERE u.id = ?""";

    private final String FIND_BY_USERNAME = """
            SELECT u.id AS user_id,
                   u.name AS user_name,
            	   u.username AS user_username,
            	   u.password AS user_password,
            	   ur.role AS user_role_role,
            	   t.id AS task_id,
            	   t.title AS task_title,
            	   t.description AS task_description,
            	   t.expiration_date AS task_expiration_date,
                   t.status AS task_status
            FROM users u
                   LEFT JOIN users_roles ur ON u.id = ur.user_id
            	   LEFT JOIN users_tasks ut ON u.id = ut.user_id
            	   LEFT JOIN tasks t ON ut.task_id = t.id
            WHERE u.username = ?""";

    private final String UPDATE = """
            UPDATE users
            SET name = ?,
                username = ?,
                password = ?
            WHERE id = ?""";

    private final String CREATE = """
            INSERT INTO users (name, username, password)
            VALUES (?, ?, ?)""";

    private final String INSERT_USER_ROLE = """
            INSERT INTO users_roles (user_id, role)
            VALUES (?, ?)""";

    private final String IS_TASK_OWNER = """
            SELECT exists(
                          SELECT 1
                          FROM users_tasks
                          WHERE user_id = ?
                          AND task_id = ?
            )""";

    private final String DELETE = """
            DELETE FROM users
            WHERE id = ?""";

    /**
     * Retrieves a user by their ID from the database.
     *
     * @param id the ID of the user to retrieve
     * @return an Optional containing the retrieved user, or empty if no user is found with the given ID
     * @throws ResourceMappingException if an error occurs while retrieving the user
     */
    @Override
    public Optional<User> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while finding user by id.");
        }
    }

    /**
     * Retrieves a user by their username from the database.
     *
     * @param username the username of the user to retrieve
     * @return an Optional containing the retrieved user, or empty if no user is found with the given username
     * @throws ResourceMappingException if an error occurs while retrieving the user by username
     */
    @Override
    public Optional<User> findByUsername(String username) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(UserRowMapper.mapRow(rs));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while finding user by username.");
        }
    }

    /**
     * Updates an existing user in the database with the provided information.
     *
     * @param user the user with updated information
     * @throws ResourceMappingException if an error occurs while updating the user
     */
    @Override
    public void update(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while updating user.");
        }
    }

    /**
     * Creates a new user in the database with the provided information.
     *
     * @param user the user to be created
     * @throws ResourceMappingException if an error occurs while creating the user
     */
    @Override
    public void create(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()){
                rs.next();
                user.setId(rs.getLong(1));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while creating user.");
        }
    }

    /**
     * Inserts a role for a user into the database.
     *
     * @param userId the ID of the user
     * @param role the role to be inserted
     * @throws ResourceMappingException if an error occurs while inserting the user role
     */
    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, userId);
            statement.setString(2, role.name());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while inserting user role.");
        }
    }

    /**
     * Checks if a user is the owner of a specific task in the database.
     *
     * @param userId the ID of the user to check
     * @param taskId the ID of the task to check
     * @return true if the user is the owner of the task, otherwise false
     * @throws ResourceMappingException if an error occurs while checking the ownership of the task
     */
    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_TASK_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, taskId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while checking if user task owner.");
        }
    }

    /**
     * Deletes a user from the database based on their ID.
     *
     * @param id the ID of the user to be deleted
     * @throws ResourceMappingException if an error occurs while deleting the user
     */
    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while deleting user.");
        }
    }
}