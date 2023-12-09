package com.nl.Nutso.model.dto;

import com.nl.Nutso.model.validation.UniqueUserEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserRegistrationDTO(String firstName,
                                  String lastName,
                                  @NotNull @Email @UniqueUserEmail String email,
                                  String password,
                                  String confirmPassword) {
}
