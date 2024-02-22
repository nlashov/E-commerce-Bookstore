package com.nl.Nutso.model.entity;

import com.nl.Nutso.model.enums.BookConditionEnum;
import com.nl.Nutso.model.enums.CategoryEnum;
import com.nl.Nutso.model.validation.YearNotInFuture;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @NotNull
    @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private double price;

    private String additionalInfo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    private String publisher;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BookConditionEnum bookCondition;

    private boolean isAvailable;

    public BookConditionEnum getBookCondition() {
        return bookCondition;
    }

    public BookEntity setBookCondition(BookConditionEnum bookCondition) {
        this.bookCondition = bookCondition;
        return this;
    }


    @YearNotInFuture
    private int year;

    @NotEmpty
    private String imageUrl;



    public UUID getUuid() {
        return uuid;
    }

    public BookEntity setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public BookEntity setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public BookEntity setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }



    public CategoryEnum getCategory() {
        return category;
    }

    public BookEntity setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public int getYear() {
        return year;
    }

    public BookEntity setYear(int year) {
        this.year = year;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BookEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public BookEntity setAvailable(boolean available) {
        this.isAvailable = available;
        return this;
    }

    public Long getId() {
        return id;
    }

    public BookEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPublisher() {
        return publisher;
    }

    public BookEntity setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }
}
