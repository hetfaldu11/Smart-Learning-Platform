package com.fm.smartlearningplatform.verification.model;

import com.fm.smartlearningplatform.user.model.Auditable;
import com.fm.smartlearningplatform.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_verifications")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "user")
@Builder
public class UserVerification extends Auditable {

    @Id
    @Column(name = "user_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "email_verified")
    @Builder.Default
    private boolean emailVerified = false;

    @Column(name = "phone_verified")
    @Builder.Default
    private boolean phoneVerified = false;

    @Column(name = "two_factor_enabled")
    @Builder.Default
    private boolean twoFactorEnabled = false;

    @Column(name = "changed_email_at")
    private LocalDateTime changedEmailAt;

    @Column(name = "changed_phone_at")
    private LocalDateTime changedPhoneAt;

    @Column(name = "changed_password_at")
    private LocalDateTime changedPasswordAt;

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;

    @Column(name = "phone_verified_at")
    private LocalDateTime phoneVerifiedAt;

    @Column(name = "two_factor_enabled_at")
    private LocalDateTime twoFactorEnabledAt;
}