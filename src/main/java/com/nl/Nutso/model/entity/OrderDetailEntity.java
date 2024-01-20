package com.nl.Nutso.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="order_detail")
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    private int totalItems;
    private double totalPrice;
    private double unitPrice;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrderEntity order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "book_id")
    private BookEntity book;

    public Long getId() {
        return id;
    }

    public OrderDetailEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public OrderDetailEntity setTotalItems(int totalItems) {
        this.totalItems = totalItems;
        return this;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public OrderDetailEntity setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public OrderDetailEntity setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public OrderDetailEntity setOrder(OrderEntity order) {
        this.order = order;
        return this;
    }

    public BookEntity getBook() {
        return book;
    }

    public OrderDetailEntity setBook(BookEntity book) {
        this.book = book;
        return this;
    }
}
