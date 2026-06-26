package com.fm.smartlearningplatform.payment.exception;

public class RefundNotAllowedException extends RefundException {

    public RefundNotAllowedException() {
        super("Refund is not allowed for this payment.");
    }

}