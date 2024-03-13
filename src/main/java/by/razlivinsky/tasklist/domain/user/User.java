package by.razlivinsky.tasklist.domain.user;

import by.razlivinsky.tasklist.domain.task.Task;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * User class represents a user with properties such as id, name, username, password, roles, and tasks.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@Data
public class User {
    private Long id;
    private String name;
    private String username;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;
    private List<Task> tasks;
}