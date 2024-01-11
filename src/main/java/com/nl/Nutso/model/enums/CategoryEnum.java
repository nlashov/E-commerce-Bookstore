package com.nl.Nutso.model.enums;

public enum CategoryEnum {

    HISTORICAL("Исторически"),
    FANTASY("Фентъзи"),
    PSYCHOLOGY("Психология");

    private final String displayName;

    CategoryEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
