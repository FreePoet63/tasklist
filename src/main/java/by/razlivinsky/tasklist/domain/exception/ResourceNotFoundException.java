package by.razlivinsky.tasklist.domain.exception;

/**
 * ResourceNotFoundException class represents an exception indicating that a requested resource was not found.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
public class ResourceNotFoundException extends RuntimeException{
    /**
     * Constructs a new ResourceNotFoundException with a specified error message.
     *
     * @param message the message explaining the resource not found exception
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}