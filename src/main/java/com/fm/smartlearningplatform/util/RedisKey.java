package com.fm.smartlearningplatform.util;

public class RedisKey {

    public static String emailOtpCooldownKey(Long userId) {
        return "otp:cooldown:email:" + userId;
    }

    public static String emailOtpKey(Long userId) {
        return "otp:email:" + userId;
    }

    public static String emailOtpAttemptKey(Long userId) {
        return "otp:attempt:email:" + userId;
    }

    public static String phoneOtpCooldownKey(Long userId) {
        return "otp:cooldown:phone:" + userId;
    }

    public static String phoneOtpKey(Long userId) {
        return "otp:phone:" + userId;
    }

    public static String phoneOtpAttemptKey(Long userId) {
        return "otp:attempt:phone:" + userId;
    }

    public static String changePasswordOtpCooldownKey(Long userId) {
        return "changepassword:cooldown:phone:" + userId;
    }

    public static String changePasswordOtpKey(Long userId) {
        return "changepassword:phone:" + userId;
    }

    public static String changePasswordOtpAttemptKey(Long userId) {
        return "changepassword:attempt:phone:" + userId;
    }


}
