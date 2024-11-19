package com.ExceptionLogger.ExceptionHandler.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "error_logs")
public class ErrorDetail {

    @Id
    private String id;

    private String errorType;

    private String errorMessage;

    private String sourceClassName;

    private String sourceMethodName;

    private int sourceLineNumber;

    private String rootCauseMessage;

    private String rootCauseClassName;

    private String stackTrace;

    private LocalDateTime timestamp;

    private String applicationName;

    private int httpStatusCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSourceClassName() {
        return sourceClassName;
    }

    public void setSourceClassName(String sourceClassName) {
        this.sourceClassName = sourceClassName;
    }

    public String getSourceMethodName() {
        return sourceMethodName;
    }

    public void setSourceMethodName(String sourceMethodName) {
        this.sourceMethodName = sourceMethodName;
    }

    public int getSourceLineNumber() {
        return sourceLineNumber;
    }

    public void setSourceLineNumber(int sourceLineNumber) {
        this.sourceLineNumber = sourceLineNumber;
    }

    public String getRootCauseMessage() {
        return rootCauseMessage;
    }

    public void setRootCauseMessage(String rootCauseMessage) {
        this.rootCauseMessage = rootCauseMessage;
    }

    public String getRootCauseClassName() {
        return rootCauseClassName;
    }

    public void setRootCauseClassName(String rootCauseClassName) {
        this.rootCauseClassName = rootCauseClassName;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
