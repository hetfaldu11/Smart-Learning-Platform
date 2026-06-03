package com.fm.smartlearningplatform.exceptionhandler.exception;

public class SessionRevokedException extends RuntimeException {
    public SessionRevokedException(String message) {
        super(message);
    }
}
