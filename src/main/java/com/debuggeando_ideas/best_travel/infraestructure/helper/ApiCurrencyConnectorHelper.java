package com.debuggeando_ideas.best_travel.infraestructure.helper;

import com.debuggeando_ideas.best_travel.infraestructure.dto.CurrencyDTO;
import java.net.URI;
import java.util.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ApiCurrencyConnectorHelper {

    private static final String BASE_CURRENCY_QUERY_PARAM = "base={base}";

    private static final String SYMBOL_CURRENCY_QUERY_PARAM = "&symbols={symbols}";

    private static final String CURRENCY_PATH = "exchangerates_data/latest";

    private final WebClient webClientCurrency;

    @Value("${api.currency}")
    private String baseCurrency;

    public ApiCurrencyConnectorHelper(@Qualifier("webClientCurrency") WebClient webClientCurrency) {
        this.webClientCurrency = webClientCurrency;
    }

    public CurrencyDTO getCurrency(Currency currency) {
        return webClientCurrency
            .get()
            .uri(uriBuilder -> uriBuilder
                .path(CURRENCY_PATH)
                .query(BASE_CURRENCY_QUERY_PARAM)
                .query(SYMBOL_CURRENCY_QUERY_PARAM)
                .build(baseCurrency, currency)
            )
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError,clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
            .bodyToMono(CurrencyDTO.class)
            .block();
    }
}
