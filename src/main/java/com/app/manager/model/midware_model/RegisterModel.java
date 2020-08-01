package com.app.manager.model.midware_model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterModel {
    @Size(min = 6, max = 30, message = "username must contain between 6 and 30 characters")
    private String username;

    @NotBlank
    @Email(message = "please provide valid email")
    private String email;

    @Size(min = 6, message = "password must contain at least 6 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "password must contain both uppercase and lowercase and a number")
    private String password;

    public RegisterModel() {
    }

    public RegisterModel(@NotBlank @Size(min = 6, max = 30, message = "username too short or too long") String username,
                         @NotBlank @Email String email, @NotBlank String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
