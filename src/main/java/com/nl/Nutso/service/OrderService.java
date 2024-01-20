package com.nl.Nutso.service;

import com.nl.Nutso.model.dto.PlaceOrderDTO;
import com.nl.Nutso.model.entity.CartEntity;
import com.nl.Nutso.model.entity.OrderEntity;

import java.util.List;

public interface OrderService {

    OrderEntity placeOrder(CartEntity cart, PlaceOrderDTO placeOrderDTO);
    List<OrderEntity> getAllOrdersByCustomer(String email);
    List<OrderEntity> getAllOrders();
    void acceptOrder(Long id);
    void cancelOrder(Long id);
    List<OrderEntity> getAllOrdersToAccept();
    List<OrderEntity> getAllAcceptedOrders();
}
