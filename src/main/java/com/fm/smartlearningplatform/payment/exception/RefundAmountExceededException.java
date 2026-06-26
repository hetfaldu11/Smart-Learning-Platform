package com.fm.smartlearningplatform.payment.exception;

public class RefundAmountExceededException extends RefundException {

    public RefundAmountExceededException() {
        super("Refund amount exceeds the available refundable amount.");
    }

}