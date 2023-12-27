package com.nl.Nutso.model.entity;

import com.nl.Nutso.model.enums.CategoryEnum;
import com.nl.Nutso.model.enums.ConditionEnum;
import com.nl.Nutso.model.validation.YearNotInFuture;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class BookEntity extends BaseEntity {

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ConditionEnum condition;

    private String additionalInfo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Min(1930)
    @YearNotInFuture
    private int year;

    @NotEmpty
    private String imageUrl;

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

    public BigDecimal getPrice() {
        return price;
    }

    public BookEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ConditionEnum getCondition() {
        return condition;
    }

    public BookEntity setCondition(ConditionEnum condition) {
        this.condition = condition;
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
}
