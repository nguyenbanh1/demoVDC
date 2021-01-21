package com.nguyen.demo.error;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
@Slf4j
public class ApiGlobalExceptionHandler {

    private static final int MAX_ERROR_MESSAGE_LENGTH = 120;

    /**
     * Handles {@link IllegalArgumentException}.
     * Response with {@link org.springframework.http.HttpStatus#BAD_REQUEST}.
     *
     * @param exception the given exception
     * @return customized error response.
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(BAD_REQUEST)
    public Error handleIllegalArgumentException(IllegalArgumentException exception) {
        return Error.builder()
                .code(ErrorCode.INVALID_PARAMETER.getMessage())
                .message(StringUtils.abbreviate(exception.getMessage(), MAX_ERROR_MESSAGE_LENGTH))
                .build();
    }

    /**
     * Handles {@link ApplicationException}.
     *
     * @param ex the given exception
     * @return customized error response.
     */
    @ExceptionHandler(value = {ApplicationException.class})
    public Error handleNclException(ApplicationException ex) {
        return Error.builder()
                .code(Integer.toString(ex.getErrorCode().getCode()))
                .message(StringUtils.abbreviate(ex.getMessage(), MAX_ERROR_MESSAGE_LENGTH))
                .build();
    }


    /**
     * Handles {@link MethodArgumentNotValidException}.
     * Response with {@link org.springframework.http.HttpStatus#BAD_REQUEST}.
     *
     * @param ex the given exception
     * @return customized error response.
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        BindingResult result = ex.getBindingResult();

        List<FieldError> fieldErrors = new ArrayList<>();

        fieldErrors.addAll(result.getGlobalErrors().stream()
                .map(f -> new FieldError(f.getObjectName(), f.getClass().getSimpleName(),
                        f.getDefaultMessage(), f.getCode()))
                .collect(Collectors.toList()));

        fieldErrors.addAll(result.getFieldErrors().stream()
                .map(f -> new FieldError(f.getObjectName(), f.getField(),
                        f.getDefaultMessage(), f.getCode()))
                .collect(Collectors.toList()));

        return ResponseEntity.badRequest().body(
                Error.builder()
                        .code(ErrorCode.INVALID_PARAMETER.getMessage())
                        .message(StringUtils.abbreviate(ex.getMessage(), MAX_ERROR_MESSAGE_LENGTH))
                        .details(fieldErrors)
                        .build());

    }


    /**
     * Handles the rest of {@link Exception}.
     * Response with {@link org.springframework.http.HttpStatus#INTERNAL_SERVER_ERROR}.
     *
     * @param exception the given exception
     * @return customized error response.
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public Error handleGeneralException(Exception exception) {
        log.error("Internal error", exception);
        return Error.builder()
                .code(ErrorCode.INVALID_PARAMETER.getMessage())
                .message(StringUtils.abbreviate(exception.getMessage(), MAX_ERROR_MESSAGE_LENGTH))
                .target(exception.getClass().getSimpleName())
                .build();
    }

    /**
     * Handles the rest of {@link HttpRequestMethodNotSupportedException}.
     * Response with {@link org.springframework.http.HttpStatus#NOT_FOUND}.
     * It usually handles the existing endpoint but no handler (HTTP method) found.
     *
     * @param exception the given exception
     * @return customized error response.
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(METHOD_NOT_ALLOWED)
    public Error handleEndPointException(Exception exception) {
        log.error("The requested resource was not found", exception);
        return Error.builder()
                .code(ErrorCode.ERR_METHOD_NOT_ALLOWED.getMessage())
                .message(StringUtils.abbreviate(exception.getMessage(), MAX_ERROR_MESSAGE_LENGTH))
                .target(exception.getClass().getSimpleName())
                .build();
    }


    /**
     * Handles the rest of {@link AccessDeniedException}.
     * Response with {@link org.springframework.http.HttpStatus#FORBIDDEN}.
     *
     * @param exception the given exception
     * @return customized error response.
     */
    @ExceptionHandler(value = {AccessDeniedException.class})
    @ResponseStatus(FORBIDDEN)
    public Error handleUnAuthorized(AccessDeniedException exception) {
        return Error.builder()
                .code(ErrorCode.FORBIDDEN.getMessage())
                .message(exception.getMessage())
                .target(ErrorCode.FORBIDDEN.getMessage())
                .build();
    }

    /**
     * Handles the rest of {@link AuthenticationException}.
     * Response with {@link org.springframework.http.HttpStatus#UNAUTHORIZED}.
     *
     * @param exception the given exception
     * @return customized error response.
     */
    @ExceptionHandler(value = {AuthenticationException.class})
    @ResponseStatus(UNAUTHORIZED)
    public Error handleUnAuthorized(AuthenticationException exception) {
        return Error.builder()
                .code(ErrorCode.ERR_UNAUTHORIZED.getMessage())
                .message(exception.getMessage())
                .target(ErrorCode.ERR_UNAUTHORIZED.getMessage())
                .build();
    }
}
