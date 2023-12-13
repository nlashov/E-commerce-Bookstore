package com.nl.Nutso.service;

import com.nl.Nutso.model.events.UserRegisteredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserActivationService {

    @EventListener(UserRegisteredEvent.class)
    public void userRegistered(UserRegisteredEvent event) {
        //TODO: add activation links
        System.out.println("User with email" + event.getUserEmail());

    }
}
