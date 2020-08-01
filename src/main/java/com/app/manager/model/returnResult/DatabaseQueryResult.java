package com.app.manager.model.returnResult;

import org.springframework.http.HttpStatus;

public class DatabaseQueryResult {
    private boolean Success;
    private String Description;
    private HttpStatus HttpStatus;

    public DatabaseQueryResult(boolean success, String description, HttpStatus httpStatus) {
        Success = success;
        Description = description;
        HttpStatus = httpStatus;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.HttpStatus = httpStatus;
    }
}
