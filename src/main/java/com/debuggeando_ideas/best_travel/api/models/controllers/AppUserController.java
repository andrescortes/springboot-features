package com.debuggeando_ideas.best_travel.api.models.controllers;

import com.debuggeando_ideas.best_travel.domain.entities.documents.AppUserDocument;
import com.debuggeando_ideas.best_travel.infraestructure.abstractservice.IModifyUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class AppUserController {

    private final IModifyUserService modifyUserService;

    @Operation(
        summary = "Enable or disable a user",
        description = "Enable or disable a user"
    )
    @PatchMapping("/enabled-or-disabled")
    public ResponseEntity<Map<String, Boolean>> enabledOrDisabled(
        @RequestParam final String username) {
        return ResponseEntity.ok(modifyUserService.enabled(username));
    }

    @Operation(
        summary = "Get all users",
        description = "Get all users"
    )
    @GetMapping
    public ResponseEntity<List<AppUserDocument>> getUsers() {
        return ResponseEntity.ok(modifyUserService.getUsers().isEmpty()
            ? Collections.emptyList()
            : modifyUserService.getUsers());
    }

    @Operation(
        summary = "Add role to a user",
        description = "Add role to a user"
    )
    @PatchMapping("/add-role")
    public ResponseEntity<Map<String, Set<String>>> addRole(
        @RequestParam final String username,
        @RequestParam final String role) {
        return ResponseEntity.ok(modifyUserService.addRole(username, role));
    }

    @Operation(
        summary = "Remove role from a user",
        description = "Remove role from a user"
    )
    @PatchMapping("/remove-role")
    public ResponseEntity<Map<String, Set<String>>> removeRole(
        @RequestParam final String username,
        @RequestParam final String role) {
        return ResponseEntity.ok(modifyUserService.removeRole(username, role));
    }
}
