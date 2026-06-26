package com.fm.smartlearningplatform.payment.exception;

public abstract class RefundException extends PaymentException {

    protected RefundException(String message) {
        super(message);
    }

}