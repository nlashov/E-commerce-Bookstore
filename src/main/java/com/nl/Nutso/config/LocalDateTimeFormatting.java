package com.nl.Nutso.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeFormatting {

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateTime.format(formatter);
    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = formatDateTime(now);
        System.out.println(formattedDateTime);
    }
}

