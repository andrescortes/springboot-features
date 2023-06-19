package com.debuggeando_ideas.best_travel.util;

import java.time.LocalDateTime;
import java.util.Random;

public class BestTravelUtil {

    private static final Random random = new Random();

    private BestTravelUtil() {

    }

    public static LocalDateTime getRandomSoon() {
        int randomNumber = random.nextInt(5 - 2) + 2;
        return LocalDateTime.now().plusHours(randomNumber);
    }
    public static LocalDateTime getRandomLater() {
        int randomNumber = random.nextInt(12 - 6) + 6;
        return LocalDateTime.now().plusHours(randomNumber);
    }

}
