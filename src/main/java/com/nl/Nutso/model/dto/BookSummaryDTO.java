package com.nl.Nutso.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record BookSummaryDTO(
        String uuid,
        String title,
        String author,
        BigDecimal price,
        String imageUrl,
        boolean isAvailable) {


}
