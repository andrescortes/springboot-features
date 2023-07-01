package com.debuggeando_ideas.best_travel.api.models.controllers;

import com.debuggeando_ideas.best_travel.api.models.request.TicketRequest;
import com.debuggeando_ideas.best_travel.api.models.response.BaseErrorResponse;
import com.debuggeando_ideas.best_travel.api.models.response.ErrorResponse;
import com.debuggeando_ideas.best_travel.api.models.response.TicketResponse;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.ITicketService;
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
@RequestMapping("/ticket")
@AllArgsConstructor
@Tag(name = "Ticket", description = "Ticket API")
public class TicketController {

    private final ITicketService ticketService;

    @Operation(
        summary = "Save in system a ticket",
        description = "Create a ticket"
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
    public ResponseEntity<TicketResponse> create(@Valid @RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.create(request));
    }

    @Operation(
        summary = "Read a ticket",
        description = "Read a ticket"
    )
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> read(@PathVariable final UUID id) {
        return ResponseEntity.ok(ticketService.read(id));
    }

    @Operation(
        summary = "Update a ticket",
        description = "update a ticket"
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
    public ResponseEntity<TicketResponse> update(@PathVariable final UUID id,
        @Valid @RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.update(id, request));
    }

    @Operation(
        summary = "Delete a ticket",
        description = "Delete a ticket"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<TicketResponse> delete(@PathVariable final UUID id) {
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Get the price of a ticket",
        description = "Get the price of a ticket"
    )
    @GetMapping
    public ResponseEntity<Map<String, BigDecimal>> getFlyPrice(@RequestParam final Long flyId) {
        return ResponseEntity.ok(
            Collections.singletonMap("flyPrice", ticketService.findPriceById(flyId)));

    }
}
