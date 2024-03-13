package by.razlivinsky.tasklist.web.mappers;

import by.razlivinsky.tasklist.domain.task.Task;
import by.razlivinsky.tasklist.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * TaskMapper interface
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@Mapper(componentModel = "spring")
public interface TaskMapper {
    /**
     * To dto task dto.
     *
     * @param task the task
     * @return the task dto
     */
    TaskDto toDto(Task task);

    /**
     * To dto list.
     *
     * @param tasks the tasks
     * @return the list
     */
    List<TaskDto> toDto(List<Task> tasks);

    /**
     * To entity task.
     *
     * @param dto the dto
     * @return the task
     */
    Task toEntity(TaskDto dto);
}