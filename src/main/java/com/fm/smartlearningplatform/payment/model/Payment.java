package com.fm.smartlearningplatform.payment.model;

import com.fm.smartlearningplatform.common.model.UserDateAudit;
import com.fm.smartlearningplatform.payment.model.enums.PaymentGatewayType;
import com.fm.smartlearningplatform.payment.model.enums.PaymentMethod;
import com.fm.smartlearningplatform.payment.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "payments",
        indexes = {

                @Index(
                        name = "idx_payment_number",
                        columnList = "payment_number"
                ),

                @Index(
                        name = "idx_payment_order",
                        columnList = "order_id"
                ),

                @Index(
                        name = "idx_payment_status",
                        columnList = "status"
                ),

                @Index(
                        name = "idx_gateway_payment",
                        columnList = "gateway_payment_id"
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_number", nullable = false, unique = true, length = 50)
    private String paymentNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentGatewayType gateway;

    @Column(name = "gateway_order_id", length = 150)
    private String gatewayOrderId;

    @Column(name = "gateway_payment_id", length = 150)
    private String gatewayPaymentId;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentStatus status;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name = "failure_reason", length = 500)
    private String failureReason;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}