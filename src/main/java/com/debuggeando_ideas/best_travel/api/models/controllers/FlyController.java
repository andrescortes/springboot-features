package com.debuggeando_ideas.best_travel.api.models.controllers;

import com.debuggeando_ideas.best_travel.api.models.response.FlyResponse;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.IFlyService;
import com.debuggeando_ideas.best_travel.util.enums.SortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Fly", description = "Fly API")
@RestController
@RequestMapping("/fly")
@AllArgsConstructor
public class FlyController {

    private final IFlyService flyService;

    @Operation(
        summary = "Read all flights",
        description = "Read all flights"
    )
    @GetMapping
    public ResponseEntity<Page<FlyResponse>> findAll(
        @RequestParam Integer page,
        @RequestParam Integer size,
        @RequestHeader(required = false) SortType sortType) {
        if (Objects.isNull(sortType)) {
            sortType = SortType.NONE;
        }
        Page<FlyResponse> flyResponses = flyService.readAll(page, size, sortType);
        return flyResponses.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(flyResponses);
    }

    @Operation(
        summary = "Read a flight by less price",
        description = "Read a flight by less price"
    )
    @GetMapping("/less-price")
    public ResponseEntity<Set<FlyResponse>> readLessPrice(
        @RequestParam BigDecimal price) {
        Set<FlyResponse> flyResponses = flyService.readLessPrice(price);
        return flyResponses.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(flyResponses);
    }

    @Operation(
        summary = "Read a flight by between price",
        description = "Read a flight by between price"
    )
    @GetMapping("/between-price")
    public ResponseEntity<Set<FlyResponse>> readBetweenPrice(
        @RequestParam BigDecimal priceMin,
        @RequestParam BigDecimal priceMax) {
        Set<FlyResponse> flyResponses = flyService.readBetweenPrice(priceMin, priceMax);
        return flyResponses.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(flyResponses);
    }

    @Operation(
        summary = "Read a flight by between price",
        description = "Read a flight by between price"
    )
    @GetMapping("/origin-destination")
    public ResponseEntity<Set<FlyResponse>> readBetweenPrice(
        @RequestParam String origin,
        @RequestParam String destination) {
        Set<FlyResponse> flyResponses = flyService.readByOriginAndDestination(origin, destination);
        return flyResponses.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(flyResponses);
    }
}
