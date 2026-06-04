package com.fm.smartlearningplatform.security.ratelimit;

import com.fm.smartlearningplatform.exceptionhandler.exception.RateLimitExceededException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public void consume(RateLimitType type, String key) {
        Bucket bucket = buckets.computeIfAbsent(type + ":" + key, k -> createBucket(type));
        if (!bucket.tryConsume(1)) {
            throw new RateLimitExceededException("Too many requests. Please try again later.");
        }
    }

    private Bucket createBucket(RateLimitType type) {
        return switch (type) {
            case LOGIN -> Bucket.builder()
                    .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1))))
                    .build();

            case REGISTER -> Bucket.builder()
                            .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofHours(1))))
                            .build();

            case VERIFY_OTP -> Bucket.builder()
                            .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(10))))
                            .build();

            case FORGOT_PASSWORD -> Bucket.builder()
                            .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofHours(1))))
                            .build();

            case RESET_PASSWORD -> Bucket.builder()
                            .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofHours(1))))
                            .build();

            case REFRESH_TOKEN -> Bucket.builder()
                            .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1))))
                            .build();
            case CHANGE_PASSWORD -> Bucket.builder()
                            .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofHours(1))))
                            .build();

        };
    }
}