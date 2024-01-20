package com.nl.Nutso.service.impl;

import com.nl.Nutso.model.dto.PlaceOrderDTO;
import com.nl.Nutso.model.entity.*;
import com.nl.Nutso.repository.AddressRepository;
import com.nl.Nutso.repository.OrderDetailRepository;
import com.nl.Nutso.repository.OrderRepository;
import com.nl.Nutso.repository.UserRepository;
import com.nl.Nutso.service.CartService;
import com.nl.Nutso.service.OrderService;
import com.nl.Nutso.service.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final AddressRepository addressRepository;


    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, UserRepository userRepository, CartService cartService, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.addressRepository = addressRepository;
    }



    @Override
    @Transactional
    public OrderEntity placeOrder(CartEntity cart, PlaceOrderDTO placeOrderDTO) {
        // Create and save the OrderEntity
        OrderEntity order = new OrderEntity();
        order.setUser(cart.getUser());
        order.setOrderDate(new Date());
        order.setOrderStatus("Pending");
        order.setAccept(false);
        order.setNote(placeOrderDTO.note());
        order.setPaymentMethod(placeOrderDTO.paymentMethod());
        order.setAddress(mapAddress(placeOrderDTO));

        // Save the OrderEntity to get its ID
        order = orderRepository.save(order);


        for (CartItemEntity item : cart.getCartItems()) {
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setOrder(order);
            orderDetail.setBook(item.getBook());
            orderDetail.setTotalItems(cart.getTotalItems());
            orderDetail.setUnitPrice(item.getBook().getPrice());
            orderDetail.setTotalPrice(cart.getTotalPrice());
            orderDetailRepository.save(orderDetail);
        }


        // Delete the cart after saving orders
        cartService.deleteCartById(cart.getId());

        return order;
    }

    @Override
    public List<OrderEntity> getAllOrdersByCustomer(String email) {
        UserEntity customer = userRepository.findByEmail(email).
                orElseThrow(() -> new ObjectNotFoundException("User with email " + email + " not found!"));
        return customer.getOrders();
    }



    @Override
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<OrderEntity> getAllOrdersToAccept() {
        return orderRepository.findByIsAcceptFalse().stream().toList();
    }

    @Override
    public List<OrderEntity> getAllAcceptedOrders() {
        return orderRepository.findByIsAcceptTrue().stream().toList();
    }

    @Override
    public void acceptOrder(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Order with id " + id + " not found!"));
        order.setAccept(true);
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(Long id) {
        orderRepository.deleteById(id);
    }

    private AddressEntity mapAddress(PlaceOrderDTO placeOrderDTO) {
        AddressEntity address = new AddressEntity();
        address.setStreet(placeOrderDTO.street());
        address.setCity(placeOrderDTO.city());
        address.setRegion(placeOrderDTO.region());
        address.setZipCode(placeOrderDTO.zipCode());
        return addressRepository.save(address);
    }
}
