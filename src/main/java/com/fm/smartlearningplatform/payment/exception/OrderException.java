package com.fm.smartlearningplatform.payment.exception;

public abstract class OrderException extends PaymentException {

    protected OrderException(String message) {
        super(message);
    }

}