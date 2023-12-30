package com.nl.Nutso.model.dto;

import com.nl.Nutso.model.enums.BookConditionEnum;
import com.nl.Nutso.model.enums.CategoryEnum;
import com.nl.Nutso.model.validation.YearNotInFuture;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record BookDetailDTO(
        String uuid,
        String title,
        String author,
        BigDecimal price,
        BookConditionEnum bookCondition,
        String additionalInfo,
        CategoryEnum category,
        Integer year,
        String imageUrl) {

    public String summary() {
        return title + " " + author + ", " + year;
    }
}
