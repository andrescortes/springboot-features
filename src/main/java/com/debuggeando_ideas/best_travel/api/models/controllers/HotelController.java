package com.debuggeando_ideas.best_travel.api.models.controllers;

import com.debuggeando_ideas.best_travel.api.models.response.HotelResponse;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.IHotelService;
import com.debuggeando_ideas.best_travel.util.SortType;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel")
@AllArgsConstructor
public class HotelController {

    private final IHotelService hotelService;

    @GetMapping
    public ResponseEntity<Page<HotelResponse>> readAll(
        @RequestParam Integer page,
        @RequestParam Integer size,
        @RequestHeader(required = false) SortType sortType
    ) {
        if (Objects.isNull(sortType)) {
            sortType = SortType.NONE;
        }
        Page<HotelResponse> hotelResponses = hotelService.readAll(page, size, sortType);
        return hotelResponses.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(hotelResponses);
    }

    @GetMapping("/less-price")
    public ResponseEntity<Set<HotelResponse>> readLessPrice(
        @RequestParam BigDecimal price
    ) {
        Set<HotelResponse> hotelResponses = hotelService.readLessPrice(price);
        return hotelResponses.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(hotelResponses);
    }

    @GetMapping("/between-price")
    public ResponseEntity<Set<HotelResponse>> readBetweenPrice(
        @RequestParam BigDecimal priceMin,
        @RequestParam BigDecimal priceMax
    ) {
        Set<HotelResponse> hotelResponses = hotelService.readBetweenPrice(priceMin, priceMax);
        return hotelResponses.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(hotelResponses);
    }

    @GetMapping("/rating")
    public ResponseEntity<Set<HotelResponse>> readBetweenPrice(
        @RequestParam Integer rating
    ) {
        if (rating > 4) {
            rating = 4;
        }
        if (rating < 1) {
            rating = 1;
        }
        Set<HotelResponse> hotelResponses = hotelService.readGreaterThanRating(rating);
        return hotelResponses.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(hotelResponses);
    }
}
