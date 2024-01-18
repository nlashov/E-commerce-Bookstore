package com.nl.Nutso.model.entity;

import com.nl.Nutso.model.enums.PaymentMethodEnum;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private Date orderDate;

    private String orderStatus;

    private String note;

    private PaymentMethodEnum paymentMethod;

    private boolean isAccept;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetailEntity> orderDetailList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressEntity address;

    public Long getId() {
        return id;
    }

    public OrderEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public OrderEntity setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public OrderEntity setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public String getNote() {
        return note;
    }

    public OrderEntity setNote(String note) {
        this.note = note;
        return this;
    }

    public PaymentMethodEnum getPaymentMethod() {
        return paymentMethod;
    }

    public OrderEntity setPaymentMethod(PaymentMethodEnum paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public OrderEntity setAccept(boolean accept) {
        isAccept = accept;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public OrderEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public List<OrderDetailEntity> getOrderDetailList() {
        return orderDetailList;
    }

    public OrderEntity setOrderDetailList(List<OrderDetailEntity> orderDetailList) {
        this.orderDetailList = orderDetailList;
        return this;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public OrderEntity setAddress(AddressEntity address) {
        this.address = address;
        return this;
    }
}
