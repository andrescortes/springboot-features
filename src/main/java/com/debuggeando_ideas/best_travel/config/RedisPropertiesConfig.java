package com.debuggeando_ideas.best_travel.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "cache.redis")
@PropertySource(value = "classpath:configs/redis.properties")
public class RedisPropertiesConfig {

    private String address;
    private String password;
}
