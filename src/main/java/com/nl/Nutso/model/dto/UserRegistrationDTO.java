package com.nl.Nutso.model.dto;

import com.nl.Nutso.model.validation.FieldMatch;
import jakarta.validation.constraints.*;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "passwords should match"
)
public class UserRegistrationDTO {

    @NotNull
    @Size(min = 2, max = 20)
    public String firstName;

    @NotNull
    @Size(min = 2, max = 20)
    public String lastName;

    @NotNull
    @Email
    public String email;

    @NotNull
    @Size(min = 5, max = 20)
    public String password;

    @NotNull
    @Size(min = 5, max = 20)
    public String confirmPassword;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegistrationDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegistrationDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
