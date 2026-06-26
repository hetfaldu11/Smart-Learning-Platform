package com.fm.smartlearningplatform.payment.repository;

import com.fm.smartlearningplatform.payment.model.Payment;
import com.fm.smartlearningplatform.payment.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentNumber(String paymentNumber);

    Optional<Payment> findByGatewayPaymentId(String gatewayPaymentId);

    List<Payment> findAllByOrderId(Long orderId);

    List<Payment> findAllByStatus(PaymentStatus status);

}