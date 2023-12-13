package com.nl.Nutso.model.dto;

import com.nl.Nutso.model.validation.FieldMatch;
import com.nl.Nutso.model.validation.UniqueUserEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@FieldMatch(
        first = "password",
        second = "confirmPassword",
        message = "passwords should match"
)
public record UserRegistrationDTO(@NotEmpty String firstName,
                                  @NotEmpty String lastName,
                                  @NotNull @Email @UniqueUserEmail String email,
                                  String password,
                                  String confirmPassword) {

    public String fullName() {
        return firstName + " " + lastName;
    }
}
