package com.debuggeando_ideas.best_travel.api.models.response;

import java.util.Set;
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
public class TourResponse {

    private Long id;
    private Set<UUID> ticketIds;
    private Set<UUID> reservationIds;
}
