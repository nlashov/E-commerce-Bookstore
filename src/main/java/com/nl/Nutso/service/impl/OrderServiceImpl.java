package com.nl.Nutso.service.impl;

import com.nl.Nutso.config.LocalDateTimeFormatting;
import com.nl.Nutso.model.dto.PlaceOrderDTO;
import com.nl.Nutso.model.entity.*;
import com.nl.Nutso.model.events.OrderPlacedEvent;
import com.nl.Nutso.repository.AddressRepository;
import com.nl.Nutso.repository.OrderDetailRepository;
import com.nl.Nutso.repository.OrderRepository;
import com.nl.Nutso.repository.UserRepository;
import com.nl.Nutso.service.CartService;
import com.nl.Nutso.service.EmailService;
import com.nl.Nutso.service.OrderService;
import com.nl.Nutso.service.exception.ObjectNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final AddressRepository addressRepository;
    private final ApplicationEventPublisher appEventPublisher;


    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, UserRepository userRepository, CartService cartService, AddressRepository addressRepository, ApplicationEventPublisher appEventPublisher, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.addressRepository = addressRepository;
        this.appEventPublisher = appEventPublisher;
    }


    @Override
    @Transactional
    public OrderEntity placeOrder(CartEntity cart, PlaceOrderDTO placeOrderDTO) {

        OrderEntity order = createOrder(cart, placeOrderDTO);
        setOrderAddress(order, placeOrderDTO);
        order = orderRepository.save(order);
        processOrderDetails(order, cart);
        cartService.deleteCartById(cart.getId());
        appEventPublisher.publishEvent(new OrderPlacedEvent(this, getOrderDetails(order)));
        return order;

    }

    private OrderEntity createOrder(CartEntity cart, PlaceOrderDTO placeOrderDTO) {
        OrderEntity order = new OrderEntity();
        order.setUser(cart.getUser());
        String formattedDateTime = LocalDateTimeFormatting.formatDateTime(LocalDateTime.now());
        order.setOrderDate(formattedDateTime);
        order.setOrderStatus("Pending");
        order.setAccept(false);
        order.setNote(placeOrderDTO.note());
        order.setPaymentMethod(placeOrderDTO.paymentMethod());
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

    private void setOrderAddress(OrderEntity order, PlaceOrderDTO placeOrderDTO) {
        AddressEntity address = new AddressEntity();
        address.setStreet(placeOrderDTO.street());
        address.setCity(placeOrderDTO.city());
        address.setRegion(placeOrderDTO.region());
        address.setZipCode(placeOrderDTO.zipCode());
        address.setPhoneNumber(placeOrderDTO.phoneNumber());
        addressRepository.save(address);
        order.setAddress(address);
    }

    private void processOrderDetails(OrderEntity order, CartEntity cart) {
        List<OrderDetailEntity> orderDetailList = new ArrayList<>();

        for (CartItemEntity item : cart.getCartItems()) {
            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setOrder(order);
            orderDetail.setBook(item.getBook());
            orderDetail.setTotalItems(cart.getTotalItems());
            orderDetail.setUnitPrice(item.getBook().getPrice());
            orderDetail.setTotalPrice(cart.getTotalPrice());
            orderDetailRepository.save(orderDetail);
            orderDetailList.add(orderDetail);
        }

        order.setOrderDetailList(orderDetailList);
    }

    private String getBookTitles(OrderEntity order) {
        List<String> bookInfoList = order.getOrderDetailList().stream()
                .map(orderDetail -> String.format("%s - %s",
                        orderDetail.getBook().getTitle(), orderDetail.getBook().getAuthor()))
                .collect(Collectors.toList());

        return String.join("\n", bookInfoList);
    }

    private String getOrderDetails(OrderEntity order) {
        String orderInfo = String.format("Поръчка с номер: %s беше направена от %s %s.",
                order.getId(), order.getUser().getFirstName(), order.getUser().getLastName());

        String bookDetails = String.format("Поръчаните книги са:\n%s", getBookTitles(order));

        String paymentInfo = String.format("Начин на плащане: %s", order.getPaymentMethod().getDisplayName());

        return String.join("\n", orderInfo, bookDetails, paymentInfo);
    }
}
