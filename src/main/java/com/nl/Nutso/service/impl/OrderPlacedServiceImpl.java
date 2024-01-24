package com.nl.Nutso.service.impl;

import com.nl.Nutso.model.events.OrderPlacedEvent;
import com.nl.Nutso.service.EmailService;
import com.nl.Nutso.service.OrderPlacedService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class OrderPlacedServiceImpl implements OrderPlacedService {

    private final EmailService emailService;

    public OrderPlacedServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    @EventListener(OrderPlacedEvent.class)
    public void orderPlaced(OrderPlacedEvent event) {
        String orderDetails = event.getOrderDetails();
        emailService.sendOrderNotification(orderDetails);
    }
}
