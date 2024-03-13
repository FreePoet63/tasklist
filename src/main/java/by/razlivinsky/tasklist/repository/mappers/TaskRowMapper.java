package by.razlivinsky.tasklist.repository.mappers;

import by.razlivinsky.tasklist.domain.task.Status;
import by.razlivinsky.tasklist.domain.task.Task;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskRowMapper class provides methods for mapping database result sets to Task objects.
 *
 * @author razlivinsky
 * @since 11.03.2024
 */
public class TaskRowMapper {
    /**
     * Maps a single row from the result set to a Task object.
     *
     * @param resultSet the result set obtained from the database
     * @return the Task object mapped from the result set data
     */
    @SneakyThrows
    public static Task mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            task.setTitle(resultSet.getString("task_title"));
            task.setDescription(resultSet.getString("task_description"));
            task.setStatus(Status.valueOf(resultSet.getString("task_status")));
            Timestamp timestamp = resultSet.getTimestamp("task_expiration_date");
            if (timestamp != null) {
                task.setExpirationDate(timestamp.toLocalDateTime());
            }
            return task;
        }
        return null;
    }

    /**
     * Maps multiple rows from the result set to a list of Task objects.
     *
     * @param resultSet the result set obtained from the database
     * @return a list of Task objects mapped from the result set data
     */
    @SneakyThrows
    public static List<Task> mapRows(ResultSet resultSet) {
        List<Task> tasks = new ArrayList<>();
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            if(!resultSet.wasNull()) {
                task.setTitle(resultSet.getString("task_title"));
                task.setDescription(resultSet.getString("task_description"));
                task.setStatus(Status.valueOf(resultSet.getString("task_status")));
                Timestamp timestamp = resultSet.getTimestamp("task_expiration_date");
                if (timestamp != null) {
                    task.setExpirationDate(timestamp.toLocalDateTime());
                }
                tasks.add(task);
            }
        }
        return tasks;
    }
}