package com.nl.Nutso.service.exception;

public class BookAlreadyInCartException extends RuntimeException {
    public BookAlreadyInCartException(String message) {
        super(message);
    }
}
