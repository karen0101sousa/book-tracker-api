package com.booktracker.common.exception;

public class DuplicateReadingException extends RuntimeException {

    public DuplicateReadingException(String message) {
        super(message);
    }
}