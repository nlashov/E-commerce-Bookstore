package com.nl.Nutso.model.dto;

import java.math.BigDecimal;

public record BookSummaryDTO(String title,
                             String author,
                             BigDecimal price,
                             String imageUrl) {
}
