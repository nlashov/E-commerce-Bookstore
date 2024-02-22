package com.nl.Nutso.model.enums;

public enum BookConditionEnum {
    BAD("Захабено"),
    GOOD("Добро"),
    VERY_GOOD("Много добро"),
    EXCELLENT("Отлично");

    private final String displayName;

    BookConditionEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

