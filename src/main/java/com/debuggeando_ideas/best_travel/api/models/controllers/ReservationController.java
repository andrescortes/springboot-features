package com.debuggeando_ideas.best_travel.api.models.controllers;

import com.debuggeando_ideas.best_travel.api.models.request.ReservationRequest;
import com.debuggeando_ideas.best_travel.api.models.response.BaseErrorResponse;
import com.debuggeando_ideas.best_travel.api.models.response.ErrorResponse;
import com.debuggeando_ideas.best_travel.api.models.response.ReservationResponse;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
@AllArgsConstructor
@Tag(name = "Reservation", description = "Reservation API")
public class ReservationController {

    private final IReservationService reservationService;

    @Operation(
        summary = "Save in system a reservation",
        description = "Create a reservation"
    )
    @ApiResponse(
        responseCode = "400",
        description = "When the request body is invalid",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ErrorResponse.class
            )
        )
    )
    @PostMapping
    public ResponseEntity<ReservationResponse> create(
        @Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.create(request));
    }

    @Operation(
        summary = "Read a reservation",
        description = "Read a reservation"
    )
    @ApiResponse(
        responseCode = "400",
        description = "When the reservation is not found",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ErrorResponse.class
            )
        )
    )
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> read(@PathVariable final UUID id) {
        return ResponseEntity.ok(reservationService.read(id));
    }

    @Operation(
        summary = "Update a reservation",
        description = "Update a reservation"
    )
    @ApiResponse(
        responseCode = "400",
        description = "When the request body is invalid",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ErrorResponse.class
            )
        )
    )
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponse> update(@Valid @PathVariable final UUID id,
        @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.update(id, request));
    }

    @Operation(
        summary = "Delete a reservation",
        description = "Delete a reservation"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ReservationResponse> delete(@PathVariable final UUID id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Get a reservation price",
        description = "Get a reservation price"
    )
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam final Long flyId) {
        return ResponseEntity.ok(
            Collections.singletonMap("hotelPrice", reservationService.findPriceById(flyId)));
    }
}
