package com.debuggeando_ideas.best_travel.api.models.request;

import java.util.Set;
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
public class TourRequest {

    private String customerId;
    private Set<TourFlyRequest> flights;
    private Set<TourHotelRequest> hotels;
}
