package com.app.manager.model.returnResult;

import java.util.List;

public class ValidationResult {
    private boolean Success;
    private List<String> Description;
    public ValidationResult(boolean success, List<String> description) {
        Success = success;
        Description = description;
    }
    public boolean isSuccess() {return Success; }
    public void setSuccess(boolean success) { Success = success; }
    public List<String> getDescription() { return Description; }
    public void setDescription(List<String> description) { Description = description; }
}
