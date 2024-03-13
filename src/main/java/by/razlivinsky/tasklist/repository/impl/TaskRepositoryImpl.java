package by.razlivinsky.tasklist.repository.impl;

import by.razlivinsky.tasklist.domain.exception.ResourceMappingException;
import by.razlivinsky.tasklist.domain.task.Task;
import by.razlivinsky.tasklist.repository.DataSourceConfig;
import by.razlivinsky.tasklist.repository.TaskRepository;
import by.razlivinsky.tasklist.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * TaskRepositoryImpl class provides an implementation of TaskRepository interface for interacting with the database.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT t.id                AS task_id,
                   t.title             AS task_title,
            	   t.description       AS task_description,
            	   t.expiration_date   AS task_expiration_date,
            	   t.status            AS task_status
            FROM tasks t
            WHERE id = ?""";

    private final String FIND_ALL_BY_USER_ID = """
            SELECT t.id                AS task_id,
                   t.title             AS task_title,
            	   t.description       AS task_description,
            	   t.expiration_date   AS task_expiration_date,
            	   t.status            AS task_status
            FROM tasks t
                    JOIN users_tasks ut ON t.id = ut.task_id
            WHERE ut.user_id = ?""";

    private final String ASSIGN = """
            INSERT INTO users_tasks (task_id, user_id)
            VALUES (?, ?)""";

    private final String UPDATE = """
            UPDATE tasks
            SET title = ?,
                description = ?,
            	expiration_date = ?,
            	status = ?
            WHERE id = ?""";

    private final String CREATE = """
            INSERT INTO tasks (title, description, expiration_date, status)
            VALUES (?, ?, ?, ?)""";

    private final String DELETE = """
            DELETE FROM tasks
            WHERE id = ?""";

    /**
     * Retrieves a task by its ID from the database.
     *
     * @param id the ID of the task to retrieve
     * @return an Optional containing the retrieved task, or empty if no task is found with the given ID
     * @throws ResourceMappingException if an error occurs while retrieving the task
     */
    @Override
    public Optional<Task> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                return Optional.ofNullable(TaskRowMapper.mapRow(rs));
            }
        } catch (SQLException throables) {
            throw new ResourceMappingException("Error while finding user by id.");
        }
    }

    /**
     * Retrieves all tasks associated with a specific user from the database.
     *
     * @param userId the ID of the user whose tasks are to be retrieved
     * @return a list of tasks associated with the given user
     * @throws ResourceMappingException if an error occurs while retrieving the tasks
     */
    @Override
    public List<Task> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                return TaskRowMapper.mapRows(rs);
            }
        } catch (SQLException throables) {
            throw new ResourceMappingException("Error while finding all by user.");
        }
    }

    /**
     * Assigns a task to a specific user in the database.
     *
     * @param taskId the ID of the task to be assigned
     * @param userId the ID of the user to whom the task is to be assigned
     * @throws ResourceMappingException if an error occurs while assigning the task
     */
    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN);
            statement.setLong(1, taskId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException throables) {
            throw new ResourceMappingException("Error while assigning to user.");
        }
    }

    /**
     * Updates an existing task in the database with the provided information.
     *
     * @param task the task with updated information
     * @throws ResourceMappingException if an error occurs while updating the task
     */
    @Override
    public void update(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, task.getTitle());
            if (task.getDescription() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, task.getDescription());
            }
            if (task.getExpirationDate() == null) {
                statement.setNull(3, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            }
            statement.setString(4, task.getStatus().name());
            statement.setLong(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException throables) {
            throw new ResourceMappingException("Error while updating task.");
        }
    }

    /**
     * Creates a new task in the database with the provided information.
     *
     * @param task the task to be created
     * @throws ResourceMappingException if an error occurs while creating the task
     */
    @Override
    public void create(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getTitle());
            if (task.getDescription() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, task.getDescription());
            }
            if (task.getExpirationDate() == null) {
                statement.setNull(3, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            }
            statement.setString(4, task.getStatus().name());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()){
                rs.next();
                task.setId(rs.getLong(1));
            }
        } catch (SQLException throables) {
            throw new ResourceMappingException("Error while creating task.");
        }
    }

    /**
     * Deletes a task from the database based on its ID.
     *
     * @param id the ID of the task to be deleted
     * @throws ResourceMappingException if an error occurs while deleting the task
     */
    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throables) {
            throw new ResourceMappingException("Error while deleting task.");
        }
    }
}