package org.jordicunillerarivera.spaceship.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.jordicunillerarivera.spaceship.config.AppConstants;
import org.jordicunillerarivera.spaceship.dto.CreateSpaceshipDTO;
import org.jordicunillerarivera.spaceship.dto.SpaceshipDTO;
import org.jordicunillerarivera.spaceship.service.SpaceshipService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {
    private final SpaceshipService service;

    @Autowired
    public SpaceshipController (SpaceshipService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = AppConstants.GET_ALL_SPACESHIPS, description = AppConstants.GET_ALL_SPACESHIPS_PAGINATED)
    public ResponseEntity<Page<SpaceshipDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = AppConstants.GET_SPACESHIP_BY_ID)
    @ApiResponses({
            @ApiResponse(responseCode = AppConstants.E200, description = AppConstants.SPACESHIP_FOUND),
            @ApiResponse(responseCode = AppConstants.E404, description = AppConstants.SPACESHIP_NOT_FOUND)
    })
    public ResponseEntity<SpaceshipDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/search")
    @Operation(summary = AppConstants.SEARCH_SPACESHIPS_BY_NAME)
    public ResponseEntity<Page<SpaceshipDTO>> searchByName(@RequestParam String name,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.searchByName(name, pageable));
    }

    @PostMapping
    @Operation(summary = AppConstants.CREATE_SPACESHIP)
    @ApiResponses({
            @ApiResponse(responseCode = AppConstants.E201, description = AppConstants.SPACESHIP_CREATED),
            @ApiResponse(responseCode = AppConstants.E400, description = AppConstants.INVALID_DATA)
    })
    public ResponseEntity<SpaceshipDTO> create(@Valid @RequestBody CreateSpaceshipDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = AppConstants.UPDATE_SPACESHIP)
    @ApiResponses({
            @ApiResponse(responseCode = AppConstants.E200, description = AppConstants.SPACESHIP_UPDATED),
            @ApiResponse(responseCode = AppConstants.E404, description = AppConstants.SPACESHIP_NOT_FOUND)
    })
    public ResponseEntity<SpaceshipDTO> update(@PathVariable Long id, @Valid @RequestBody CreateSpaceshipDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = AppConstants.DELETE_SPACESHIP)
    @ApiResponses({
            @ApiResponse(responseCode = AppConstants.E204, description = AppConstants.SPACESHIP_DELETED),
            @ApiResponse(responseCode = AppConstants.E404, description = AppConstants.SPACESHIP_NOT_FOUND)
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
