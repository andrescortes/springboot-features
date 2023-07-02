package com.debuggeando_ideas.best_travel.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@AllArgsConstructor
public class WebClientConfig {

    private final CurrencyPropertiesConfig properties;

    @Bean(name = "webClientCurrency")
    public WebClient webClientCurrency() {
        return WebClient.builder()
            .baseUrl(properties.getUrl())
            .defaultHeader(properties.getHeader(), properties.getApiKey())
            .filter(logResponse())
            .build();
    }

    @Bean(name = "webClient")
    public WebClient webClient() {
        return WebClient.builder()
            .filter(logResponse())
            .build();
    }


    // This method returns filter function which will log response data
    private static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response status: {}", clientResponse.statusCode());
            clientResponse
                .headers()
                .asHttpHeaders()
                .forEach((name, values) -> values.forEach(value -> log.info("header: {} = value: {}", name, value)));
            return Mono.just(clientResponse);
        });
    }
}
