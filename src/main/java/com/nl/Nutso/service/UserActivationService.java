package com.nl.Nutso.service;

import com.nl.Nutso.model.events.UserRegisteredEvent;

public interface UserActivationService {

    void userRegistered(UserRegisteredEvent event);

    String createActivationCode(String userEmail);
}
