package com.nl.Nutso.model.events;

import org.springframework.context.ApplicationEvent;

public class OrderPlacedEvent extends ApplicationEvent {

    private final String orderDetails;

    public OrderPlacedEvent(Object source, String orderDetails) {
        super(source);
        this.orderDetails = orderDetails;
    }

    public String getOrderDetails() {
        return orderDetails;
    }
}
