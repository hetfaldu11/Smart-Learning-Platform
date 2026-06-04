package com.fm.smartlearningplatform.security.ratelimit;

public enum RateLimitType {

    LOGIN,
    REGISTER,
    VERIFY_OTP,
    FORGOT_PASSWORD,
    RESET_PASSWORD,
    REFRESH_TOKEN,
    CHANGE_PASSWORD
}