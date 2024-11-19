package com.ExceptionLogger.ExceptionHandler.service;

import com.ExceptionLogger.ExceptionHandler.exception.ConflictException;
import com.ExceptionLogger.ExceptionHandler.exception.ForbiddenException;
import com.ExceptionLogger.ExceptionHandler.exception.ResourceNotFoundException;
import com.ExceptionLogger.ExceptionHandler.exception.UnauthorizedAccessException;
import com.ExceptionLogger.ExceptionHandler.model.ErrorDetail;
import com.ExceptionLogger.ExceptionHandler.repository.ErrorDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;
import javax.naming.ServiceUnavailableException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import org.springframework.web.HttpMediaTypeNotSupportedException;

@ControllerAdvice
public class ErrorDetailService {

    private static final Logger logger = LoggerFactory.getLogger(ErrorDetailService.class);
    private final ErrorDetailRepository errorDetailRepository;

    @Value("${application.name}")
    private String applicationName;

    @Autowired
    public ErrorDetailService(ErrorDetailRepository errorDetailRepository) {
        this.errorDetailRepository = errorDetailRepository;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleException(Exception ex) {
        ErrorDetail errorDetail = new ErrorDetail();

        errorDetail.setErrorType(ex.getClass().getName());
        errorDetail.setErrorMessage(ex.getMessage());
        errorDetail.setStackTrace(Arrays.toString(ex.getStackTrace()));
        errorDetail.setTimestamp(LocalDateTime.now());

        StackTraceElement element = ex.getStackTrace()[0];
        errorDetail.setSourceClassName(element.getClassName());
        errorDetail.setSourceMethodName(element.getMethodName());
        errorDetail.setSourceLineNumber(element.getLineNumber());

        Throwable rootCause = ex.getCause();
        if (rootCause != null) {
            errorDetail.setRootCauseMessage(rootCause.getMessage());
            errorDetail.setRootCauseClassName(rootCause.getClass().getName());
        }

        errorDetail.setApplicationName(applicationName);

        HttpStatus status = determineHttpStatus(ex);
        errorDetail.setHttpStatusCode(status.value());

        logger.error("Error occurred: {}", ex.getMessage(), ex);

        errorDetailRepository.save(errorDetail);

        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private HttpStatus determineHttpStatus(Exception ex) {
        // Java exceptions
        if (ex instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof NullPointerException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof IllegalStateException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof TimeoutException) {
            return HttpStatus.GATEWAY_TIMEOUT;
        } else if (ex instanceof SQLException) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else if (ex instanceof ArithmeticException) {
            return HttpStatus.BAD_REQUEST;
        }

        else if (ex instanceof ResourceNotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (ex instanceof UnauthorizedAccessException) {
            return HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof ForbiddenException) {
            return HttpStatus.FORBIDDEN;
        } else if (ex instanceof ConflictException) {
            return HttpStatus.CONFLICT;
        } else if (ex instanceof AuthenticationException) {
            return HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof AccessDeniedException) {
            return HttpStatus.FORBIDDEN;
        } else if (ex instanceof DataIntegrityViolationException) {
            return HttpStatus.CONFLICT;
        } else if (ex instanceof MethodArgumentNotValidException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpMessageNotReadableException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            return HttpStatus.METHOD_NOT_ALLOWED;
        } else if (ex instanceof NoHandlerFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            return HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        } else if (ex instanceof ServiceUnavailableException) {
            return HttpStatus.SERVICE_UNAVAILABLE;
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof TypeMismatchException) {
            return HttpStatus.BAD_REQUEST;
        }

        else if (ex instanceof WebExchangeBindException) {
            return HttpStatus.BAD_REQUEST;
        }

        else if (ex instanceof Exception) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
