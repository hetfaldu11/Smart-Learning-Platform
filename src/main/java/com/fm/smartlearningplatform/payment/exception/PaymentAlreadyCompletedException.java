package com.fm.smartlearningplatform.payment.exception;

public class PaymentAlreadyCompletedException extends PaymentException {

    public PaymentAlreadyCompletedException() {
        super("Payment has already been completed.");
    }

}