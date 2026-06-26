package com.fm.smartlearningplatform.payment.exception;

public abstract class InvoiceException extends PaymentException {

    protected InvoiceException(String message) {
        super(message);
    }

}