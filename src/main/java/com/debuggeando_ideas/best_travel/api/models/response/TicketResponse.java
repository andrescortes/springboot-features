package com.debuggeando_ideas.best_travel.api.models.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class TicketResponse {

    private UUID id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime departureDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime arrivalDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime purchaseDate;
    private BigDecimal price;
    private FlyResponse fly;

}
