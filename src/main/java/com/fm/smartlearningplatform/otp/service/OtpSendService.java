package com.fm.smartlearningplatform.otp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OtpSendService {

    private final JavaMailSender mailSender;

    @Async
    public void sendChangePasswordOtp(String to, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Forgot Password Again?");
        message.setText(
                "Hey, short-memory person!\n \n" +
                        "You forgot your important password again\n" +
                        "No worries, here’s your chance to change it.\n \n" +
                        "Your OTP:\n" + otp + "\n \n" +
                        "Next time, remember your password carefully");

        mailSender.send(message);
    }

    @Async
    public void sendEmailVerificationOtp(String to, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Email verification.");
        message.setText("Your OTP:\n" + otp + "\n \n");

        mailSender.send(message);
    }

    @Async
    public void sendPhoneVerificationOtp(String to, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Phone verification.");
        message.setText("Your OTP:\n" + otp + "\n \n");

        mailSender.send(message);
    }
}
