package com.fm.smartlearningplatform.payment.generator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

@Service
public class DatabaseSequenceService {

    @PersistenceContext
    private EntityManager entityManager;

    public long nextOrderSequence() {

        return nextValue("order_sequence");

    }
    public long nextPaymentSequence() {

        return nextValue("payment_sequence");

    }
    public long nextInvoiceSequence() {

        return nextValue("invoice_sequence");

    }
    public long nextRefundSequence() {

        return nextValue("refund_sequence");

    }

    private long nextValue(String sequenceName) {

        return ((Number) entityManager
                .createNativeQuery(
                        "SELECT nextval('" + sequenceName + "')"
                )
                .getSingleResult())
                .longValue();

    }

}