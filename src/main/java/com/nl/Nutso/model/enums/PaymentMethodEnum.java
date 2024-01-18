package com.nl.Nutso.model.enums;

public enum PaymentMethodEnum {
    AT_DELIVERY("Наложен платеж"),
    CARD("С карта"),
    GOOGLE_PAY("Google pay");


    private final String displayName;

    PaymentMethodEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
