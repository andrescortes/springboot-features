package com.debuggeando_ideas.best_travel.api.models.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseErrorResponse implements Serializable {

    private String status;
    private Integer code;

}
