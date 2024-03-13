package by.razlivinsky.tasklist.web.controller;

import by.razlivinsky.tasklist.domain.exception.AccessDeniedException;
import by.razlivinsky.tasklist.domain.exception.ExceptionBody;
import by.razlivinsky.tasklist.domain.exception.ResourceMappingException;
import by.razlivinsky.tasklist.domain.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ControllerAdvice class handles exception handling for the application's controllers.
 *
 * @author razlivinsky
 * @since 13.03.2024
 */
@RestControllerAdvice
public class ControllerAdvice {
    /**
     * Handle Resource Not Found Exception and return an ExceptionBody.
     *
     * @param e the ResourceNotFoundException
     * @return the ExceptionBody containing the error message
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(ResourceNotFoundException e) {
        return new ExceptionBody(e.getMessage());
    }

    /**
     * Handle Resource Mapping Exception and return an ExceptionBody.
     *
     * @param e the ResourceMappingException
     * @return the ExceptionBody containing the error message
     */
    @ExceptionHandler(ResourceMappingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleResourceMapping(ResourceMappingException e) {
        return new ExceptionBody(e.getMessage());
    }

    /**
     * Handle Illegal State Exception and return an ExceptionBody.
     *
     * @param e the IllegalStateException
     * @return the ExceptionBody containing the error message
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalState(IllegalStateException e) {
        return new ExceptionBody(e.getMessage());
    }

    /**
     * Handle Access Denied Exception and return an ExceptionBody.
     *
     * @return the ExceptionBody containing the error message
     */
    @ExceptionHandler({AccessDeniedException.class, org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDenied() {
        return new ExceptionBody("Access. denied.");
    }

    /**
     * Handle Method Argument Not Valid Exception and return an ExceptionBody with validation errors.
     *
     * @param e the MethodArgumentNotValidException
     * @return the ExceptionBody containing the validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        exceptionBody.setErrors(errors.stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
        return exceptionBody;
    }

    /**
     * Handle Constraint Violation Exception and return an ExceptionBody with validation errors.
     *
     * @param e the ConstraintViolationException
     * @return the ExceptionBody containing the validation errors
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolation(ConstraintViolationException e) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed.");
        exceptionBody.setErrors(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage()
                )));
        return exceptionBody;
    }

    /**
     * Handle all other Exceptions and return a generic ExceptionBody.
     *
     * @param e the Exception
     * @return the generic ExceptionBody containing the error message
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(Exception e) {
        return new ExceptionBody("Internal error.");
    }
}