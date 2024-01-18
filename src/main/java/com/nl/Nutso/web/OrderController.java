package com.nl.Nutso.web;

import com.nl.Nutso.service.OrderService;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
}
