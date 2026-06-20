package com.fm.smartlearningplatform.exceptionhandler.exception;

public class InvalidFileException
        extends RuntimeException {

    public InvalidFileException(String message)
    {
        super(message);
    }
}
