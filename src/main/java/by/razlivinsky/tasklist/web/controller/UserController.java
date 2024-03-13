package by.razlivinsky.tasklist.web.controller;

import by.razlivinsky.tasklist.domain.task.Task;
import by.razlivinsky.tasklist.domain.user.User;
import by.razlivinsky.tasklist.service.TaskService;
import by.razlivinsky.tasklist.service.UserService;
import by.razlivinsky.tasklist.web.dto.task.TaskDto;
import by.razlivinsky.tasklist.web.dto.user.UserDto;
import by.razlivinsky.tasklist.web.dto.validation.OnCreate;
import by.razlivinsky.tasklist.web.dto.validation.OnUpdate;
import by.razlivinsky.tasklist.web.mappers.TaskMapper;
import by.razlivinsky.tasklist.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController class handles HTTP requests related to user operations.
 *
 * @author razlivinsky
 * @since 10.03.2024
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;
    private final TaskService taskService;

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    /**
     * Update an existing user based on the provided UserDto.
     *
     * @param dto the UserDto containing updated user information
     * @return the UserDto of the updated user
     */
    @PutMapping
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    /**
     * Get user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the UserDto for the specified user ID
     */
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    /**
     * Delete user by ID.
     *
     * @param id the ID of the user to be deleted
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

    /**
     * Retrieve tasks associated with a specific user by their ID.
     *
     * @param id the ID of the user for whom tasks are to be retrieved
     * @return a list of TaskDto objects associated with the specified user
     */
    @GetMapping("/{id}/tasks")
    public List<TaskDto> getTaskByUserId(@PathVariable Long id) {
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    /**
     * Create a new task associated with a specific user.
     *
     * @param id  the ID of the user for whom the task is created
     * @param dto the TaskDto containing the information of the task to be created
     * @return the TaskDto of the created task
     */
    @PostMapping("/{id}/tasks")
    public TaskDto createTask(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);
    }
}