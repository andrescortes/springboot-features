package com.debuggeando_ideas.best_travel.api.models.request;

import jakarta.validation.constraints.Size;
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
    @Size(min = 18, max = 20, message = "The field customerId must be between 18 and 20 characters")
    private String customerId;
    @Size(min =1, message = "The field flights must be at least 1")
    private Set<TourFlyRequest> flights;
    @Size(min = 1, message = "The field hotels must be at least 1")
    private Set<TourHotelRequest> hotels;
}
