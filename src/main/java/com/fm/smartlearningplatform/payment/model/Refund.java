package com.fm.smartlearningplatform.payment.model;

import com.fm.smartlearningplatform.common.model.UserDateAudit;
import com.fm.smartlearningplatform.payment.model.enums.CurrencyCode;
import com.fm.smartlearningplatform.payment.model.enums.RefundStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "refunds",
        indexes = {

                @Index(
                        name = "idx_refund_number",
                        columnList = "refund_number"
                ),

                @Index(
                        name = "idx_refund_payment",
                        columnList = "payment_id"
                ),

                @Index(
                        name = "idx_refund_status",
                        columnList = "status"
                ),

                @Index(
                        name = "idx_gateway_refund",
                        columnList = "gateway_refund_id"
                )

        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Refund extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refund_number", nullable = false, unique = true, length = 50)
    private String refundNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @Column(name = "gateway_refund_id", length = 150)
    private String gatewayRefundId;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyCode currency;

    @Column(length = 500)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RefundStatus status;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;
}