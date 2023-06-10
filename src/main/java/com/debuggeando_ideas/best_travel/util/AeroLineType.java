package com.debuggeando_ideas.best_travel.util;

import lombok.Getter;

@Getter
public enum AeroLineType {
    AERO_GOLD("aero_gold"),
    BLUE_SKY("blue_sky");

    private String aeroLine;

    AeroLineType(String aeroLine) {
        this.aeroLine = aeroLine;
    }
}
