package com.debuggeando_ideas.best_travel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources(value = {
    @PropertySource("classpath:configs/redis.properties"),
    @PropertySource("classpath:configs/client-security.properties"),
    @PropertySource("classpath:configs/api-currency.properties"),
})
public class GeneralPropertiesConfig {

}
