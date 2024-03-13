package by.razlivinsky.tasklist.repository.mappers;

import by.razlivinsky.tasklist.domain.task.Task;
import by.razlivinsky.tasklist.domain.user.Role;
import by.razlivinsky.tasklist.domain.user.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * UserRowMapper class provides a method for mapping database result sets to User objects.
 *
 * @author razlivinsky
 * @since 12.03.2024
 */
public class UserRowMapper {
    /**
     * Maps a row from the result set to a User object.
     *
     * @param resultSet the result set obtained from the database
     * @return the User object mapped from the result set data
     */
    @SneakyThrows
    public static User mapRow(ResultSet resultSet) {
        Set<Role> roles = new HashSet<>();
        while (resultSet.next()) {
            roles.add(Role.valueOf(resultSet.getString("user_role_role")));
        }
        resultSet.beforeFirst();
        List<Task> tasks = TaskRowMapper.mapRows(resultSet);
        resultSet.beforeFirst();
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("user_name"));
            user.setUsername(resultSet.getString("user_username"));
            user.setPassword(resultSet.getString("user_password"));
            user.setRoles(roles);
            user.setTasks(tasks);
            return user;
        }
        return null;
    }
}