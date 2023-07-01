package com.debuggeando_ideas.best_travel.api.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class TicketRequest {

    @NotBlank(message = "The idClient is required")
    private String idClient;
    @NotBlank(message = "The idFly is required")
    @Positive(message = "The idFly must be positive")
    private Long idFly;
}
