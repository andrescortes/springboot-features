package com.debuggeando_ideas.best_travel.config;

import com.debuggeando_ideas.best_travel.domain.app.Constant;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@AllArgsConstructor
@Configuration
@EnableScheduling
@EnableCaching
public class RedisConfig {

    private final RedisPropertiesConfig properties;

    @Bean
    public RedissonClient getRedissonClient() {
        Config config = new Config();
        config.useSingleServer()
            .setAddress(properties.getAddress())
            .setPassword(properties.getPassword());
        return Redisson.create(config);
    }

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();
        config.put(Constant.HOTEL_CACHE_NAME, new CacheConfig());
        config.put(Constant.FLY_CACHE_NAME, new CacheConfig());
        return new RedissonSpringCacheManager(redissonClient, config);
    }

    @CacheEvict(cacheNames =
        {
            Constant.HOTEL_CACHE_NAME,
            Constant.FLY_CACHE_NAME,
        },
        allEntries = true)
    @Scheduled(cron = Constant.SCHEDULED_RESET_CACHE)
    @Async
    public void deleteCache() {
        log.info("Deleting all caches");
    }
}
