package org.jordicunillerarivera.spaceship.service.impl;

import org.jordicunillerarivera.spaceship.dto.CreateSpaceshipDTO;
import org.jordicunillerarivera.spaceship.dto.SpaceshipDTO;
import org.jordicunillerarivera.spaceship.model.Spaceship;
import org.jordicunillerarivera.spaceship.repository.SpaceshipRepository;
import org.jordicunillerarivera.spaceship.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class SpaceshipServiceImplTest {

    @Mock
    private SpaceshipRepository repository;

    @InjectMocks
    private SpaceshipServiceImpl service;

    private Spaceship spaceship;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        spaceship = new Spaceship();
        spaceship.setId(1L);
        spaceship.setName("X-Wing");
        spaceship.setModel("T-65B");
        spaceship.setManufacturer("Star Wars");
    }


    @Test
    void testFindAll() {
        Mockito.when(repository.findAll(PageRequest.of(0,10))).thenReturn(new PageImpl<>(List.of(new Spaceship())));
        var page = service.findAll(PageRequest.of(0,10));
        assertEquals(1, page.getTotalElements());
    }


    @Test
    void testCreate() {
        Mockito.when(repository.save(any(Spaceship.class))).thenAnswer(i -> { Spaceship s = i.getArgument(0); s.setId(1L); return s; });
        var dto = new CreateSpaceshipDTO("Test", "M1", "Manu", 2);
        var created = service.create(dto);
        assertNotNull(created);
        assertEquals(1L, created.id());
    }

    @Test
    void testFindById_found() {
        when(repository.findById(1L)).thenReturn(Optional.of(spaceship));

        SpaceshipDTO result = service.findById(1L);

        assertNotNull(result);
        assertEquals("X-Wing", result.name());
        verify(repository).findById(1L);
    }

    // Este test cubre lambda$findById$0
    @Test
    void testFindById_notFound_shouldThrowException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void testSearchByName() {
        Pageable pageable = PageRequest.of(0,10);
        when(repository.findByNameContainingIgnoreCase("wing", pageable))
                .thenReturn(new PageImpl<>(List.of(spaceship)));

        Page<SpaceshipDTO> results = service.searchByName("wing", pageable);

        assertEquals(1, results.getTotalElements());
        assertEquals("X-Wing", results.getContent().get(0).name());
        verify(repository).findByNameContainingIgnoreCase("wing", pageable);
    }

    @Test
    void testUpdate_success() {
        when(repository.findById(1L)).thenReturn(Optional.of(spaceship));
        when(repository.save(any(Spaceship.class))).thenAnswer(i -> i.getArgument(0));

        CreateSpaceshipDTO updated = new CreateSpaceshipDTO("Y-Wing",
                "BTL-A4",
                "Star Wars",
                2);

        SpaceshipDTO result = service.update(1L, updated);

        assertEquals("Y-Wing", result.name());
        verify(repository).save(any(Spaceship.class));
    }

    // Cubre lambda$update$0
    @Test
    void testUpdate_notFound_shouldThrowException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        CreateSpaceshipDTO updated = new CreateSpaceshipDTO("Ghost",
                null,
                null,
                null);


        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, updated));
    }

    @Test
    void testDelete_success() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void testDelete_notFound_shouldThrowException() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
    }

}
