package com.fm.smartlearningplatform.payment.exception;

public class InvalidPaymentAmountException extends PaymentException {

    public InvalidPaymentAmountException() {
        super("Invalid payment amount.");
    }

}