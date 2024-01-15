package com.nl.Nutso.service;

import com.nl.Nutso.model.dto.UserRegistrationDTO;
import com.nl.Nutso.model.entity.UserEntity;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistrationDTO);

    UserEntity getUserByEmail(String email);
}
