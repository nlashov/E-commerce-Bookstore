package com.nl.Nutso.service.impl;

import com.nl.Nutso.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;

@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final String adminEmail;

    public EmailServiceImpl(TemplateEngine templateEngine,
                            JavaMailSender javaMailSender,
                            @Value("${mail.admin}") String adminEmail) {

        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.adminEmail = adminEmail;
    }

    @Override
    public void sendRegistrationEmail(String userEmail, String userName, String activationCode) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        try {
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setFrom(adminEmail);
            mimeMessageHelper.setReplyTo(adminEmail);
            mimeMessageHelper.setSubject("Nutso bookshop - activate your account!");
            mimeMessageHelper.setText(generateRegistrationEmailBody(userName, activationCode), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendOrderNotification(String orderDetails) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setTo(adminEmail);
            mimeMessageHelper.setFrom(adminEmail);
            mimeMessageHelper.setSubject("Имате нова поръчка " + LocalDate.now());
            mimeMessageHelper.setText(generateOrderPlacedEmailBody(orderDetails), true);

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

    private String generateOrderPlacedEmailBody(String orderDetails) {
        Context context = new Context();
        context.setVariable("orderDetails", orderDetails);
        return templateEngine.process("order/order-placed-email", context);
    }

}
