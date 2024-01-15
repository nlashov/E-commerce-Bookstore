package com.nl.Nutso.model.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Set;


@Entity
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private int totalItems;

    private double totalPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private Set<CartItemEntity> cartItems;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

//    private String status;
//    private String shippingAddress;


    public int getTotalItems() {
        return totalItems;
    }

    public CartEntity setTotalItems(int totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public CartEntity setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public Long getId() {
        return id;
    }

    public CartEntity setId(Long id) {
        this.id = id;
        return this;
    }


    public Set<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public CartEntity setCartItems(Set<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
        return this;
    }
}
