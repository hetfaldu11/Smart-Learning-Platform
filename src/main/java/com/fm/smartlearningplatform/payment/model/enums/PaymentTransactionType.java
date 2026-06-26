package com.fm.smartlearningplatform.payment.model.enums;

public enum PaymentTransactionType {

    CREATED,

    GATEWAY_ORDER_CREATED,

    PAYMENT_INITIATED,

    PAYMENT_AUTHORIZED,

    PAYMENT_CAPTURED,

    WEBHOOK_RECEIVED,

    SIGNATURE_VERIFIED,

    PAYMENT_SUCCESS,

    PAYMENT_FAILED,

    REFUND_INITIATED,

    REFUND_COMPLETED

}