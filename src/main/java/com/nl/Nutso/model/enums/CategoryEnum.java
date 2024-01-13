package com.nl.Nutso.model.enums;

public enum CategoryEnum {

    HISTORICAL("История"),
    FANTASY("Фентъзи"),
    PSYCHOLOGY("Психология"),
    CLASSICS("Художествена литература"),
    HEALTH("Здраве и благополучие"),
    SCIENCE("Наука"),
    BUSINESS("Бизнес"),
    SPORT("Спорт и туризъм"),
    ART("Изкуство"),
    CHILDREN("Детска литература"),
    CRIMI("Криминални романи и трилъри"),
    POETRY("Поезия"),
    SPIRITUAL("Духовност, митология и езотерика");


    private final String displayName;

    CategoryEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
