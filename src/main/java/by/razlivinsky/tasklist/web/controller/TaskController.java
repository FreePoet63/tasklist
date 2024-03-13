package by.razlivinsky.tasklist.web.controller;

import by.razlivinsky.tasklist.domain.task.Task;
import by.razlivinsky.tasklist.service.TaskService;
import by.razlivinsky.tasklist.web.dto.task.TaskDto;
import by.razlivinsky.tasklist.web.dto.validation.OnUpdate;
import by.razlivinsky.tasklist.web.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * TaskController class handles HTTP requests related to task operations.
 *
 * @author razlivinsky
 * @since 10.03.2024
 */
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    /**
     * Update an existing task based on the provided TaskDto.
     *
     * @param dto the TaskDto containing updated task information
     * @return the TaskDto of the updated task
     */
    @PutMapping
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task updateTask = taskService.update(task);
        return taskMapper.toDto(updateTask);
    }

    /**
     * Get task by ID.
     *
     * @param id the ID of the task to retrieve
     * @return the TaskDto for the specified task ID
     */
    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    /**
     * Delete task by ID.
     *
     * @param id the ID of the task to be deleted
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }
}