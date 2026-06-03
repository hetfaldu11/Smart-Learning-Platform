package com.fm.smartlearningplatform.verification.mapper;

import com.fm.smartlearningplatform.verification.dto.response.UserVerificationResponse;
import com.fm.smartlearningplatform.verification.model.UserVerification;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-03T19:53:27+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 26.0.1 (Oracle Corporation)"
)
@Component
public class UserVerificationMapperImpl implements UserVerificationMapper {

    @Override
    public UserVerificationResponse toResponse(UserVerification userVerification) {
        if ( userVerification == null ) {
            return null;
        }

        Long id = null;
        Boolean emailVerified = null;
        Boolean phoneVerified = null;
        LocalDateTime emailVerifiedAt = null;
        LocalDateTime phoneVerifiedAt = null;
        Boolean twoFactorEnabled = null;
        LocalDateTime twoFactorEnabledAt = null;

        id = userVerification.getId();
        emailVerified = userVerification.isEmailVerified();
        phoneVerified = userVerification.isPhoneVerified();
        emailVerifiedAt = userVerification.getEmailVerifiedAt();
        phoneVerifiedAt = userVerification.getPhoneVerifiedAt();
        twoFactorEnabled = userVerification.isTwoFactorEnabled();
        twoFactorEnabledAt = userVerification.getTwoFactorEnabledAt();

        UserVerificationResponse userVerificationResponse = new UserVerificationResponse( id, emailVerified, phoneVerified, emailVerifiedAt, phoneVerifiedAt, twoFactorEnabled, twoFactorEnabledAt );

        return userVerificationResponse;
    }
}
