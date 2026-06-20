package com.fm.smartlearningplatform.security.ratelimit;

import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.redisson.Bucket4jRedisson;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RateLimitConfig {

    @Value("${redis.rate_limit.database.address}")
    private String REDIS_RATE_LIMIT_DATABASE_ADDRESS;

    @Bean
    public ProxyManager<String> proxyManager(RedissonClient redissonClient) {
        return Bucket4jRedisson.casBasedBuilder(
                ((Redisson) redissonClient).getCommandExecutor()
        ).build();
    }

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(REDIS_RATE_LIMIT_DATABASE_ADDRESS);
        return  Redisson.create(config);
    }
}