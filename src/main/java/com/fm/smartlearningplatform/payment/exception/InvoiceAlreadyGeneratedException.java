package com.fm.smartlearningplatform.payment.exception;

public class InvoiceAlreadyGeneratedException extends InvoiceException {

    public InvoiceAlreadyGeneratedException() {
        super("Invoice has already been generated.");
    }

}