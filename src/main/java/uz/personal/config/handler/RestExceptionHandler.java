package uz.personal.config.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.personal.exception.GenericNotFoundException;
import uz.personal.exception.IdRequiredException;
import uz.personal.exception.RequestObjectNullPointerException;
import uz.personal.exception.ValidationException;
import uz.personal.response.AppErrorDto;
import uz.personal.response.DataDto;

import java.net.SocketTimeoutException;

@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<DataDto<?>> handleCustomException(Exception ex, WebRequest request) {
        String message = getLastCause(ex);
        logger.error(message, ex);
        return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(message).systemName(ex.getLocalizedMessage()).build()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<DataDto<?>> handleCustomException(AccessDeniedException ex, WebRequest request) {
        String message = getLastCause(ex);
        logger.error(message, ex);
        return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(message).systemName(ex.getLocalizedMessage()).build()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public final ResponseEntity<DataDto<?>> handleUserNotFoundException(UnauthorizedUserException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(ex.getMessage()).systemName(ex.getLocalizedMessage()).build()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<DataDto<?>> handleValidationException(ValidationException ex, WebRequest request) {
        String message = getLastCause(ex);
        logger.error(message, ex);
        return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(message).systemName(ex.getLocalizedMessage()).fieldKey(ex.getKey()).build()), HttpStatus.BAD_REQUEST);
    }

    // time out error handler SocketTimeoutException
    @ExceptionHandler(SocketTimeoutException.class)
    public final ResponseEntity<DataDto<?>> handleValidationException(SocketTimeoutException ex, WebRequest request) {
        String message = getLastCause(ex);
        logger.error(message, ex);
        return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(message).systemName(ex.getLocalizedMessage()).fieldKey(ex.getMessage()).build()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequestObjectNullPointerException.class)
    public final ResponseEntity<DataDto<?>> handleRequestObjectNullPointerException(RequestObjectNullPointerException ex, WebRequest request) {
        String message = getLastCause(ex);
        logger.error(message, ex);
        return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(message).systemName(ex.getLocalizedMessage()).fieldKey(ex.getKey()).build()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericNotFoundException.class)
    public final ResponseEntity<DataDto<?>> handleRequestObjectNullPointerException(GenericNotFoundException ex, WebRequest request) {
        String message = getLastCause(ex);
        logger.error(message, ex);
        return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(message).systemName(ex.getLocalizedMessage()).fieldKey(ex.getKey()).build()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IdRequiredException.class)
    public final ResponseEntity<DataDto<?>> handleRequestObjectNullPointerException(IdRequiredException ex, WebRequest request) {
        String message = getLastCause(ex);
        logger.error(message, ex);
        return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder().friendlyMessage(message).systemName(ex.getLocalizedMessage()).fieldKey(ex.getKey()).build()), HttpStatus.BAD_REQUEST);
    }

    private String getLastCause(Throwable throwable) {
        return throwable.getCause() == null ? (throwable.getLocalizedMessage() == null ? throwable.getMessage() : throwable.getLocalizedMessage()) : getLastCause(throwable.getCause());
    }
}
