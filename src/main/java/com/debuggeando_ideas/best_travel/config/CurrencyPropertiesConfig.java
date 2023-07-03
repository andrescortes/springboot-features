package com.debuggeando_ideas.best_travel.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api")
@PropertySource(value = "classpath:configs/api-currency.properties")
public class CurrencyPropertiesConfig {

    @Value("${API_KEY}")
    private String apiKey;
    private String url;
    private String header;
    private String currency;
}
