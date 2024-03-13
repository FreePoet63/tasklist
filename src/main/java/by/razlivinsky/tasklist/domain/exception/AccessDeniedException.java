package by.razlivinsky.tasklist.domain.exception;

/**
 * AccessDeniedException class represents an exception indicating access denial during runtime.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
public class AccessDeniedException extends RuntimeException{
    /**
     * Instantiates a new Access denied exception.
     */
    public AccessDeniedException() {
        super();
    }
}