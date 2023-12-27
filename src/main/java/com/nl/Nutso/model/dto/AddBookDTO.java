package com.nl.Nutso.model.dto;

import com.nl.Nutso.model.enums.CategoryEnum;
import com.nl.Nutso.model.enums.ConditionEnum;
import com.nl.Nutso.model.validation.YearNotInFuture;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AddBookDTO(@NotEmpty String title,
                         @NotEmpty String author,
                         @Positive @NotNull BigDecimal price,
                         @NotNull ConditionEnum condition,
                         @Size(max = 200) String additionalInfo,
                         @NotNull CategoryEnum category,
                         @YearNotInFuture Integer year,
                         @NotEmpty String imageUrl) {

    public static AddBookDTO empty() {
        return new AddBookDTO(null, null, null, null, null, null, null, null);
    }
}
