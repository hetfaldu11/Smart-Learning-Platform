package com.fm.smartlearningplatform.payment.repository;

import com.fm.smartlearningplatform.payment.model.Refund;
import com.fm.smartlearningplatform.payment.model.enums.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface RefundRepository
        extends JpaRepository<Refund, Long> {

    List<Refund> findAllByPaymentId(Long paymentId);

    @Query("""
            SELECT COALESCE(SUM(r.amount),0)
            FROM Refund r
            WHERE r.payment.id = :paymentId
            AND r.status='SUCCESS'
            """)
    BigDecimal getTotalRefundedAmount(Long paymentId);

    List<Refund> findAllByStatus(RefundStatus status);

}