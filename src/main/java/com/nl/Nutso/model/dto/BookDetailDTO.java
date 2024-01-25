package com.nl.Nutso.model.dto;

import com.nl.Nutso.model.enums.BookConditionEnum;
import com.nl.Nutso.model.enums.CategoryEnum;
import com.nl.Nutso.model.validation.YearNotInFuture;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record BookDetailDTO(
        UUID uuid,
        @NotEmpty String title,
        @NotEmpty String author,
        @Positive @NotNull double price,
        @NotNull BookConditionEnum bookCondition,
        @Size(max = 200) String additionalInfo,
        @NotNull CategoryEnum category,
        @YearNotInFuture
        @NotNull(message = "Year must be provided!")
        Integer year,
        @NotEmpty String imageUrl,
        Boolean isAvailable,
        @NotEmpty String publisher) {


    public BookDetailDTO(UUID uuid, String title, String author, double price,
                         BookConditionEnum bookCondition, String additionalInfo,
                         CategoryEnum category, Integer year, String imageUrl,
                         Boolean isAvailable, String publisher) {
        this.uuid = uuid;
        this.title = title;
        this.author = author;
        this.price = price;
        this.bookCondition = bookCondition;
        this.additionalInfo = additionalInfo;
        this.category = category;
        this.year = year;
        this.imageUrl = imageUrl;
        this.isAvailable = (isAvailable != null) ? isAvailable : Boolean.TRUE;
        this.publisher = publisher;
    }

    public String summary() {
        return title + ", " + author + ", " + year + "г.";
    }

}
