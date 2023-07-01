package com.debuggeando_ideas.best_travel.api.models.controllers;

import com.debuggeando_ideas.best_travel.api.models.request.TourRequest;
import com.debuggeando_ideas.best_travel.api.models.response.TourResponse;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.ITourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tour")
@AllArgsConstructor
@Tag(name = "Tour", description = "Tour API")
public class TourController {

    private final ITourService tourService;

    @Operation(
        summary = "Save in system a tour based in list of hotel and flight",
        description = "Create a tour"
    )
    @PostMapping
    public ResponseEntity<TourResponse> createTour(@Valid @RequestBody TourRequest request) {
        return ResponseEntity.ok(tourService.create(request));
    }

    @Operation(
        summary = "Read a tour",
        description = "Read a tour"
    )
    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> readTour(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.read(id));
    }

    @Operation(
        summary = "Delete a tour",
        description = "Delete a tour"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Update a tour",
        description = "Update a tour"
    )
    @PatchMapping("/{tourId}/remove-ticket/{ticketId}")
    public ResponseEntity<Void> deleteTicket(
        @PathVariable Long tourId,
        @PathVariable UUID ticketId
    ) {
        tourService.removeTicket(tourId, ticketId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Add a ticket at a tour",
        description = "add a tour"
    )
    @PatchMapping("/{tourId}/add-ticket/{flyId}")
    public ResponseEntity<Map<String, UUID>> addTicket(
        @PathVariable Long tourId,
        @PathVariable Long flyId
    ) {
        UUID uuid = tourService.addTicket(tourId, flyId);
        Map<String, UUID> response = new HashMap<>();
        response.put("ticketId", uuid);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Remove a reservation at a tour",
        description = "remove a tour"
    )
    @PatchMapping("/{tourId}/remove-reservation/{reservationId}")
    public ResponseEntity<Void> deleteReservation(
        @PathVariable Long tourId,
        @PathVariable UUID reservationId
    ) {
        tourService.removeReservation(tourId, reservationId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Add a reservation at a tour",
        description = "add a tour"
    )
    @PatchMapping("/{tourId}/add-reservation/{hotelId}")
    public ResponseEntity<Map<String, UUID>> addReservation(
        @PathVariable Long tourId,
        @PathVariable Long hotelId,
        @RequestParam Integer totalDays
    ) {
        UUID uuid = tourService.addReservation(tourId, hotelId, totalDays);
        Map<String, UUID> response = new HashMap<>();
        response.put("reservationId", uuid);
        return ResponseEntity.ok(response);
    }
}
