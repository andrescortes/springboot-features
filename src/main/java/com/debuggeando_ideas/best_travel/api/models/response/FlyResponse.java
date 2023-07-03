package com.debuggeando_ideas.best_travel.api.models.response;

import com.debuggeando_ideas.best_travel.util.enums.AeroLineType;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class FlyResponse implements Serializable {

    private Long id;
    private Double originLat;
    private Double originLng;
    private Double destinyLng;
    private Double destinyLat;
    private String originName;
    private String destinyName;
    private AeroLineType aeroLine;
    private BigDecimal price;
}
