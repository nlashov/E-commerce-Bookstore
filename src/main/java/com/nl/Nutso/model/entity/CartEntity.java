package com.nl.Nutso.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class CartEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
//    private Long customerId;
//    private List<CartItem> items;
//    private int totalQuantity;
//    private double totalPrice;
//    private String status;
//    private String shippingAddress;
}
