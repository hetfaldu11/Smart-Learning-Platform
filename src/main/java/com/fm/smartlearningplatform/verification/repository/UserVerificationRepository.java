package com.fm.smartlearningplatform.verification.repository;

import com.fm.smartlearningplatform.verification.model.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVerificationRepository extends JpaRepository<UserVerification, Long> {

    boolean existsByIdAndEmailVerifiedIsTrue(Long id);

    boolean existsByIdAndPhoneVerifiedIsTrue(Long id);

    boolean existsByIdAndTwoFactorEnabledIsTrue(Long id);

}
