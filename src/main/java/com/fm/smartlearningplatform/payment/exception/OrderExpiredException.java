package com.fm.smartlearningplatform.payment.exception;

public class OrderExpiredException extends OrderException {

    public OrderExpiredException() {
        super("Order has expired.");
    }

}