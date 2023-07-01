package com.debuggeando_ideas.best_travel.api.models.controllers;

import com.debuggeando_ideas.best_travel.api.models.request.TicketRequest;
import com.debuggeando_ideas.best_travel.api.models.response.TicketResponse;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.ITicketService;
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
@RequestMapping("/ticket")
@AllArgsConstructor
public class TicketController {

    private final ITicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> create(@Valid @RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> read(@PathVariable final UUID id) {
        return ResponseEntity.ok(ticketService.read(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> update(@PathVariable final UUID id,
        @Valid @RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TicketResponse> delete(@PathVariable final UUID id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam final Long flyId) {
        return ResponseEntity.ok(
            Collections.singletonMap("flyPrice", ticketService.findPriceById(flyId)));

    }
}
