package com.fm.smartlearningplatform.payment.model;

import com.fm.smartlearningplatform.common.model.File;
import com.fm.smartlearningplatform.common.model.UserDateAudit;
import com.fm.smartlearningplatform.payment.model.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices", indexes = {

        @Index(name = "idx_invoice_number", columnList = "invoice_number"),

        @Index(name = "idx_invoice_order", columnList = "order_id")

})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number", nullable = false, unique = true, length = 50)
    private String invoiceNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false, unique = true)
    private Payment payment;

    @Column(name = "subtotal_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal subtotalAmount;

    @Column(name = "discount_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal discountAmount;

    @Column(name = "tax_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_pdf", unique = true)
    private File pdf;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_status", nullable = false)
    private InvoiceStatus status;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;
}