package com.nl.Nutso.model.dto;

import com.nl.Nutso.model.enums.BookConditionEnum;
import com.nl.Nutso.model.enums.CategoryEnum;

import java.math.BigDecimal;
import java.util.UUID;

public record BookDetailDTO(
        UUID uuid,
        String title,
        String author,
        BigDecimal price,
        BookConditionEnum bookCondition,
        String additionalInfo,
        CategoryEnum category,
        Integer year,
        String imageUrl,
        Boolean isAvailable) {


    public BookDetailDTO(UUID uuid, String title, String author, BigDecimal price,
                         BookConditionEnum bookCondition, String additionalInfo,
                         CategoryEnum category, Integer year, String imageUrl,
                         Boolean isAvailable) {
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
    }

    public String summary() {
        return title + ", " + author + ", " + year + "г.";
    }

}
