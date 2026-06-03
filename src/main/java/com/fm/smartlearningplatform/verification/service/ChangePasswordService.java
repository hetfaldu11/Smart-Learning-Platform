//package com.fm.smartlearningplatform.verification.service;
//
//import com.fm.smartlearningplatform.exceptionhandler.exception.InvalidPasswordException;
//import com.fm.smartlearningplatform.exceptionhandler.exception.ResourceNotFoundException;
//import com.fm.smartlearningplatform.otp.model.OtpType;
//import com.fm.smartlearningplatform.otp.model.UserOtp;
//import com.fm.smartlearningplatform.otp.repository.UserOtpRepository;
//import com.fm.smartlearningplatform.otp.service.EmailService;
//import com.fm.smartlearningplatform.security.jwt.JWTService;
//import com.fm.smartlearningplatform.user.model.User;
//import com.fm.smartlearningplatform.user.repository.UserRepository;
//import com.fm.smartlearningplatform.user.service.UserService;
//import com.fm.smartlearningplatform.verification.dto.request.ChangePasswordRequest;
//import com.fm.smartlearningplatform.verification.dto.response.ResetTokenResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//@Service
//@RequiredArgsConstructor
//
//public class ChangePasswordService {
//
//    private final UserService userService;
//    private final EmailService emailService;
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//    private final JWTService jWTService;
//    private  final UserOtpRepository userOtpRepository;
//
//
//    @Transactional
//    public void changePassword(Long id, ChangePasswordRequest request)
//    {
//        User user = getUser(id);
//
//        if (!passwordEncoder.matches(request.oldPassword(), user.getPasswordHash())) {
//            throw new InvalidPasswordException("Old password is incorrect.");
//        }
//
//        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
//
//        userRepository.save(user);
//    }
//
//    @Transactional
//    public ResetTokenResponse verifyPasswordResetOtp(
//            Long userId,
//            String otp
//    ) {
//
//        UserOtp userOtp =
//                userOtpRepository.findTopByUserIdAndTypeAndUsedFalseOrderByIdDesc(
//                                userId,
//                                OtpType.PASSWORD_RESET
//                        )
//                        .orElseThrow(
//                                () -> new ResourceNotFoundException(
//                                        "Otp not found"
//                                )
//                        );
//
//        validateOtp(otp, userOtp);
//
//        userOtp.setUsed(true);
//        userOtp.setVerifiedAt(LocalDateTime.now());
//
//        String resetToken = jWTService.generatePasswordResetToken(userId);
//
//        return new ResetTokenResponse(
//                resetToken
//        );
//    }
//
//    public User getUser(Long id) {
//        return userRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//    }
//
//}
