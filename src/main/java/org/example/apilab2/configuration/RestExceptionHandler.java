package org.example.apilab2.configuration;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;
import org.example.apilab2.configuration.web.ApiError;
import org.example.apilab2.configuration.web.ApiErrorWrapper;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ApiErrorWrapper apiErrorWrapper = processErrors(ex.getBindingResult().getAllErrors());
        return handleExceptionInternal(ex, apiErrorWrapper, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({HttpClientErrorException.class})
    protected ResponseEntity<Object> handleHttpClientError(HttpClientErrorException ex, WebRequest request) {
        return createResponseEntity(ex, new HttpHeaders(), ex.getStatusCode(), request);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidation(ValidationException ex, WebRequest request) {
        ApiErrorWrapper apiErrors = message(HttpStatus.BAD_REQUEST, ex);
        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        return handleExceptionInternal(ex, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DataAccessException.class})
    protected ResponseEntity<Object> handleDataAccess(DataAccessException ex, WebRequest request) {
        return handleExceptionInternal(ex, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return handleExceptionInternal(ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handle500(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, null, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
                                                             HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        if (Objects.isNull(body)) {
            ApiErrorWrapper apiErrors = message((HttpStatus) status, ex);
            return new ResponseEntity<>(apiErrors, headers, status);
        }
        return new ResponseEntity<>(body, headers, status);
    }

    protected ApiErrorWrapper message(HttpStatus httpStatus, Exception ex) {
        return message(buildApiError(httpStatus, ex));
    }

    protected ApiErrorWrapper message(ApiError error) {
        ApiErrorWrapper errors = new ApiErrorWrapper();
        errors.addApiError(error);
        return errors;
    }

    protected ApiErrorWrapper processErrors(List<ObjectError> errors) {
        ApiErrorWrapper dto = new ApiErrorWrapper();
        errors.forEach(objError -> {
            if (objError instanceof FieldError fieldError) {
                String msg = fieldError.getDefaultMessage();
                dto.addFieldError(fieldError.getClass().getSimpleName(), "Invalid Attribute",
                        fieldError.getField(), msg);
            } else {
                String msg = objError.getDefaultMessage();
                dto.addFieldError(objError.getClass().getSimpleName(), "Invalid Attribute", "base", msg);
            }
        });
        return dto;
    }

    private ApiError buildApiError(HttpStatus httpStatus, Exception ex) {
        String type = ex.getClass().getSimpleName();
        String description = StringUtils.defaultIfBlank(ex.getMessage(), type);
        String source = "base";

        if (ex instanceof MissingServletRequestParameterException m) {
            source = m.getParameterName();
        } else if (ex instanceof MissingPathVariableException m) {
            source = m.getVariableName();
        }

        return ApiError.builder()
                .status(httpStatus.value())
                .type(type)
                .title(httpStatus.getReasonPhrase())
                .description(description)
                .source(source)
                .build();
    }
}
