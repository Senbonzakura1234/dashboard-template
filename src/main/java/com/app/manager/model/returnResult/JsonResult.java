package com.app.manager.model.returnResult;

import org.springframework.http.HttpStatus;

public class JsonResult {
    private boolean Success;
    private String Description;
    private HttpStatus HttpStatus;

    public JsonResult(boolean success, String description, HttpStatus httpStatus) {
        Success = success;
        Description = description;
        HttpStatus = httpStatus;
    }

    public static JsonResult castToJsonResult(DatabaseQueryResult result){
        if (result == null) return new JsonResult(false, "result is null",
                org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        return new JsonResult(result.isSuccess(), result.getDescription(),
                result.getHttpStatus());
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

    public org.springframework.http.HttpStatus getHttpStatus() {
        return HttpStatus;
    }

    public void setHttpStatus(org.springframework.http.HttpStatus httpStatus) {
        HttpStatus = httpStatus;
    }
}
