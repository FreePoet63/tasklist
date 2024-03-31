package by.razlivinsky.tasklist.web.dto.task;

import by.razlivinsky.tasklist.domain.task.Status;
import by.razlivinsky.tasklist.web.dto.validation.OnCreate;
import by.razlivinsky.tasklist.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * TaskDto class represents the data transfer object for tasks, containing task-related information.
 *
 * @author HP
 * @since 09.03.2024
 */
@Data
@Schema(description = "Task DTO")
public class TaskDto {
    @Schema(description = "Task Id", example = "1")
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "Title of task", example = "Do homework")
    @NotNull(message = "Title must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Title length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Schema(description = "Description of task", example = "Math, History")
    @Length(max = 255, message = "Description length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    @Schema(description = "Status of task", example = "TODO")
    private Status status;

    @Schema(description = "DateTime of task", example = "2024-03-08 22:30")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate;
}