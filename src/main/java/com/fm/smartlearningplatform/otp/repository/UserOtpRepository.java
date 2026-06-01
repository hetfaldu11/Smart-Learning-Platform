package com.fm.smartlearningplatform.otp.repository;

import com.fm.smartlearningplatform.otp.model.OtpType;
import com.fm.smartlearningplatform.otp.model.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserOtpRepository
        extends JpaRepository<UserOtp, Long> {

    Optional<UserOtp>
    findTopByUserIdAndTypeAndUsedFalseOrderByIdDesc(
            Long userId,
            OtpType type
    );
}