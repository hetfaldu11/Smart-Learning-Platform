package com.fm.smartlearningplatform.exceptionhandler.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}