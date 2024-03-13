package by.razlivinsky.tasklist.domain.exception;

/**
 * ResourceMappingException class represents an exception related to resource mapping.
 *
 * @author razlivinsky
 * @since 09.03.2024
 */
public class ResourceMappingException extends RuntimeException{
    /**
     * Constructs a new ResourceMappingException with a specified error message.
     *
     * @param message the message explaining the resource mapping exception
     */
    public ResourceMappingException(String message) {
        super(message);
    }
}