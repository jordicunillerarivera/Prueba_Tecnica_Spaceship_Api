package org.jordicunillerarivera.spaceship.controller;

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
    public ResponseEntity<Page<SpaceshipDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpaceshipDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<SpaceshipDTO>> searchByName(@RequestParam String name,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(service.searchByName(name, pageable));
    }

    @PostMapping
    public ResponseEntity<SpaceshipDTO> create(@Valid @RequestBody CreateSpaceshipDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<SpaceshipDTO> update(@PathVariable Long id, @Valid @RequestBody CreateSpaceshipDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
