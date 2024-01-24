package com.nl.Nutso.service;

import com.nl.Nutso.model.events.OrderPlacedEvent;

public interface OrderPlacedService {
    void orderPlaced(OrderPlacedEvent event);
    //TODO String generateUniqueOrderNumber();
}
