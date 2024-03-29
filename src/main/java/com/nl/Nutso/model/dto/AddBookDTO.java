package com.nl.Nutso.model.dto;

import com.nl.Nutso.model.enums.BookConditionEnum;
import com.nl.Nutso.model.enums.CategoryEnum;
import com.nl.Nutso.model.validation.YearNotInFuture;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AddBookDTO(@NotEmpty String title,
                         @NotEmpty String author,
                         @Positive @NotNull Double price,
                         @NotNull BookConditionEnum bookCondition,
                         @Size(max = 200) String additionalInfo,
                         @NotNull CategoryEnum category,
                         @YearNotInFuture
                         @NotNull(message = "Year must be provided!")
                         Integer year,
                         @NotEmpty String imageUrl,
                         @NotEmpty String publisher) {

    public static AddBookDTO empty() {
        return new AddBookDTO(null, null, null, null, null, null, null, null, null);
    }
}
