package com.fm.smartlearningplatform.model.course;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_pricing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursePricing {

    @Id
    @Column(name = "course_id")
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "price", precision = 10, scale = 2,nullable = false)
    private BigDecimal price=  BigDecimal.ZERO;

    @Column(name = "discount_price", precision = 10, scale = 2)
    private BigDecimal discountPrice;

    @Column(name="currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
