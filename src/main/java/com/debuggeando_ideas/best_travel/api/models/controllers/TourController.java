package com.debuggeando_ideas.best_travel.api.models.controllers;

import com.debuggeando_ideas.best_travel.api.models.request.TourRequest;
import com.debuggeando_ideas.best_travel.api.models.response.TourResponse;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.ITourService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tour")
@AllArgsConstructor
public class TourController {

    private final ITourService tourService;

    @PostMapping
    public ResponseEntity<TourResponse> createTour(@RequestBody TourRequest request) {
        return ResponseEntity.ok(tourService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourResponse> readTour(@PathVariable Long id) {
        return ResponseEntity.ok(tourService.read(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TourResponse> deleteTour(@PathVariable Long id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
