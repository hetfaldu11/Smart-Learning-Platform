package com.fm.smartlearningplatform.payment.repository;

import com.fm.smartlearningplatform.payment.model.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTransactionRepository
        extends JpaRepository<PaymentTransaction, Long> {

    List<PaymentTransaction> findAllByPaymentId(Long paymentId);

}