package com.app.manager.model;

public class SelectOption {
    private String value;
    private String name;
    private boolean selected;

    public SelectOption() {
    }

    public SelectOption(String value, String name, boolean selected) {
        this.value = value;
        this.name = lowerCaseExceptFirst(name);
        this.selected = selected;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    String lowerCaseExceptFirst(String input){
        if(input == null || input.isEmpty()) return "";
        var lowerCase = input.toLowerCase();
        return lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1);
    }
}
