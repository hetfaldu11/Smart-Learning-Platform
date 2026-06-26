package com.fm.smartlearningplatform.payment.exception;

public abstract class PaymentException extends RuntimeException {

    protected PaymentException(String message) {
        super(message);
    }

}