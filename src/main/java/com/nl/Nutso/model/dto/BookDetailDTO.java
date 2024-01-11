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
        String imageUrl) {

    public String summary() {
        return title + ", " + author + ", " + year + "г.";
    }
}
