package com.fm.smartlearningplatform.exceptionhandler.exception;

public class SessionExpiredException extends RuntimeException {
    public SessionExpiredException(String message) {
        super(message);
    }
}
