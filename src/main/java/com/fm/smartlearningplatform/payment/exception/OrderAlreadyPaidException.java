package com.fm.smartlearningplatform.payment.exception;

public class OrderAlreadyPaidException extends OrderException {

    public OrderAlreadyPaidException() {
        super("Order has already been paid.");
    }

}