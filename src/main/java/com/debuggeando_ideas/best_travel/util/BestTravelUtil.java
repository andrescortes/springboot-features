package com.debuggeando_ideas.best_travel.util;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

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

    public static PageRequest chooseSortType(Integer page, Integer size, SortType sortType, Sort by) {
        PageRequest pageRequest = null;
        switch (sortType) {
            case NONE -> pageRequest = PageRequest.of(page, size);
            case LOWER ->
                pageRequest = PageRequest.of(page, size, by.ascending());
            case UPPER ->
                pageRequest = PageRequest.of(page, size, by.descending());
        }
        if (Objects.isNull(pageRequest)) {
            throw new IllegalArgumentException("PageRequest is null");
        }
        return pageRequest;
    }

}
