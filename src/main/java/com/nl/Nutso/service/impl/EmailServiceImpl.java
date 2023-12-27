package com.nl.Nutso.service.impl;

import com.nl.Nutso.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {

    private TemplateEngine templateEngine;
    private JavaMailSender javaMailSender;
    private String nutsoMail;

    public EmailServiceImpl(TemplateEngine templateEngine,
                            JavaMailSender javaMailSender,
                            @Value("${mail.Nutso}") String nutsoMail) {

        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.nutsoMail = nutsoMail;
    }

    @Override
    public void sendRegistrationEmail(String userEmail, String userName, String activationCode) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setFrom(nutsoMail);
            mimeMessageHelper.setReplyTo(nutsoMail);
            mimeMessageHelper.setSubject("Nutso bookshop - activate your account!");
            mimeMessageHelper.setText(generateRegistrationEmailBody(userName, activationCode), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    private String generateRegistrationEmailBody(String userName, String activationCode) {
        Context context = new Context();
        context.setVariable("username", userName);
        context.setVariable("activation_code", activationCode);
        return templateEngine.process("registration-email", context);
    }
}
