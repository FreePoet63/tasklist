package by.razlivinsky.tasklist.domain.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * ExceptionBody class represents a body containing exception details such as message and errors.
 *
 * @author razlivinsky
 * @since 13.03.2024
 */
@Data
@RequiredArgsConstructor
public class ExceptionBody {
    private String message;
    private Map<String, String> errors;

    /**
     * Constructs a new ExceptionBody instance with a specified message.
     *
     * @param message the main message explaining the exception
     */
    public ExceptionBody(String message) {
        this.message = message;
    }
}