package com.nl.Nutso.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    private int quantity = 1;

    private double price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private CartEntity cart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private BookEntity book;

    public Long getId() {
        return id;
    }

    public CartItemEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItemEntity setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public CartItemEntity setPrice(double price) {
        this.price = price;
        return this;
    }

    public CartEntity getCart() {
        return cart;
    }

    public CartItemEntity setCart(CartEntity cart) {
        this.cart = cart;
        return this;
    }

    public BookEntity getBook() {
        return book;
    }

    public CartItemEntity setBook(BookEntity book) {
        this.book = book;
        return this;
    }
}
