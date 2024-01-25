package com.nl.Nutso.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record BookSummaryDTO(
        String uuid,
        @NotEmpty String title,
        @NotEmpty String author,
        @NotNull @Positive double price,
        @NotEmpty  String imageUrl,
        boolean isAvailable) {

}
