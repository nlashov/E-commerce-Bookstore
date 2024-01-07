package com.nl.Nutso.model.dto;

public record SearchBookDTO(String query) {

    public boolean isEmpty() {
        return (query == null || query.isEmpty());
    }


}
