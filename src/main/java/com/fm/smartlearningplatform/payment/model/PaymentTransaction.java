package com.fm.smartlearningplatform.payment.model;

import com.fm.smartlearningplatform.common.model.UserDateAudit;
import com.fm.smartlearningplatform.payment.model.enums.PaymentStatus;
import com.fm.smartlearningplatform.payment.model.enums.PaymentTransactionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "payment_transactions",
        indexes = {

                @Index(
                        name = "idx_payment_transaction_payment",
                        columnList = "payment_id"
                ),

                @Index(
                        name = "idx_payment_transaction_type",
                        columnList = "type"
                ),

                @Index(
                        name = "idx_payment_transaction_status",
                        columnList = "status"
                )
//
//                @Index(
//                        name = "idx_payment_transaction_gateway_reference",
//                        columnList = "gateway_reference_id"
//                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private PaymentTransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentStatus status;

    @Column(name = "failure_reason", length = 500)
    private String failureReason;
}