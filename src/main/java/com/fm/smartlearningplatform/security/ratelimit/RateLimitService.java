package com.fm.smartlearningplatform.security.ratelimit;//package com.fm.smartlearningplatform.security.ratelimit;
//
//import com.fm.smartlearningplatform.exceptionhandler.exception.RateLimitExceededException;
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.Refill;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//@Slf4j
//public class RateLimitService {
//
//    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
//
//    public void consume(RateLimitType type, String key) {
//        Bucket bucket = buckets.computeIfAbsent(type + ":" + key, k -> createBucket(type));
//        if (!bucket.tryConsume(1)) {
//            log.warn("Rate limit exceeded for type: {} and key: {}",
//                    type,
//                    key);
//            throw new RateLimitExceededException("Too many requests. Please try again later.");
//        }
//    }
//
//    private Bucket createBucket(RateLimitType type) {
//        return switch (type) {
//            case LOGIN -> Bucket.builder()
//                    .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1))))
//                    .build();
//
//            case REGISTER -> Bucket.builder()
//                    .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofHours(1))))
//                    .build();
//
//            case VERIFY_OTP -> Bucket.builder()
//                    .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(10))))
//                    .build();
//
//            case FORGOT_PASSWORD -> Bucket.builder()
//                    .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofHours(1))))
//                    .build();
//
//            case RESET_PASSWORD -> Bucket.builder()
//                    .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofHours(1))))
//                    .build();
//
//            case REFRESH_TOKEN -> Bucket.builder()
//                    .addLimit(Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1))))
//                    .build();
//            case CHANGE_PASSWORD -> Bucket.builder()
//                    .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofHours(1))))
//                    .build();
//
//        };
//    }
//}

//package com.fm.smartlearningplatform.security.ratelimit;
//
//import com.fm.smartlearningplatform.exceptionhandler.exception.RateLimitExceededException;
//import com.fm.smartlearningplatform.util.RateLimitKey;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class RateLimitService {
//
//    private final StringRedisTemplate stringRedisTemplate;
//
//    public void consume(RateLimitType type, String key) {
//
//        String redisKey = RateLimitKey.rateLimitKey(type.name(),key);
//
//        Long count = stringRedisTemplate.opsForValue().increment(redisKey);
//
//        if (count != null && count == 1) {
//            stringRedisTemplate.expire(redisKey, type.getDuration());
//        }
//
//        if (count != null && count > type.getLimit()) {
//            throw new RateLimitExceededException(
//                    "Too many requests. Please try again later."
//            );
//        }
//    }
//

////    local current = redis.call("INCR", KEYS[1])
////
////if current == 1 then
////    redis.call("EXPIRE", KEYS[1], ARGV[1])
////    end
////
////return current
//}

import io.github.bucket4j.BandwidthBuilder;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.BucketProxy;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final ProxyManager<String> proxyManager;

    private BucketConfiguration buildConfig(RateLimitType type) {
        return BucketConfiguration.builder()
                .addLimit(BandwidthBuilder.builder()
                        .capacity(type.getCapacity())
                        .refillGreedy(
                                type.getRefillTokens(),
                                Duration.ofMinutes(type.getRefillMinutes())
                        )
                        .build())
                .build();
    }

    public boolean tryConsume(String key, RateLimitType type) {
        String bucketKey = key + ":" + type.name();

        BucketProxy bucket = proxyManager.builder()
                .build(bucketKey, () -> buildConfig(type));

        return bucket.tryConsume(1);
    }
}