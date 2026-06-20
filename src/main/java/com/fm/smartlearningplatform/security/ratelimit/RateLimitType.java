package com.fm.smartlearningplatform.security.ratelimit;

import lombok.Getter;

@Getter
public enum RateLimitType {

    LOGIN          (5,  10, 1),
    OTP_SEND       (3,  3,  1),
    OTP_VERIFY     (5,  5,  1),
    REGISTER       (5,  5,  1),
    GENERAL_API    (100, 100, 1);

    private final long capacity;
    private final long refillTokens;
    private final long refillMinutes;

    RateLimitType(long capacity, long refillTokens, long refillMinutes) {
        this.capacity     = capacity;
        this.refillTokens = refillTokens;
        this.refillMinutes = refillMinutes;
    }
}