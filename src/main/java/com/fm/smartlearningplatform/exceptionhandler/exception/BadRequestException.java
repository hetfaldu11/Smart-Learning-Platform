package com.fm.smartlearningplatform.exceptionhandler.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
