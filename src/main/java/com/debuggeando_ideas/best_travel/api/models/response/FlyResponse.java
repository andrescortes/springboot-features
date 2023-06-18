package com.debuggeando_ideas.best_travel.api.models.response;

import com.debuggeando_ideas.best_travel.util.AeroLineType;
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
public class FlyResponse {

    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLng;
    private Double destinyLat;
    private String originName;
    private String destinyName;
    private AeroLineType aeroLine;
}
