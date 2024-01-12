package com.nl.Nutso.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.math.BigDecimal;


@Entity
public class CartEntity extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private int totalItems;

    private BigDecimal totalPrices;

    private Set<CartItemEntity> cartItem;

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
