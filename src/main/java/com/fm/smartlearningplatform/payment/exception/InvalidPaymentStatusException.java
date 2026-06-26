package com.fm.smartlearningplatform.payment.exception;

public class InvalidPaymentStatusException extends PaymentException {

    public InvalidPaymentStatusException(String status) {
        super("Invalid payment status: " + status);
    }

}