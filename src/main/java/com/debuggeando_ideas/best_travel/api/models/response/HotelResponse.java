package com.debuggeando_ideas.best_travel.api.models.response;

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
public class HotelResponse implements Serializable {

    private Long id;
    private String name;
    private String address;
    private Integer rating;
    private BigDecimal price;

}
