package com.nl.Nutso.service;

public interface EmailService {

    void sendRegistrationEmail(String userEmail,
                               String userName,
                               String activationCode);
}
