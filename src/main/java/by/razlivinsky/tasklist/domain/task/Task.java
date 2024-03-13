package by.razlivinsky.tasklist.domain.task;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Task class represents a task with properties like id, title, description, status, and expiration date.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
@Data
public class Task {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;
}