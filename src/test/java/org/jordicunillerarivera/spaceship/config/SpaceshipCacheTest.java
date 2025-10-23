package org.jordicunillerarivera.spaceship.config;

import org.jordicunillerarivera.spaceship.dto.SpaceshipDTO;
import org.jordicunillerarivera.spaceship.model.Spaceship;
import org.jordicunillerarivera.spaceship.repository.SpaceshipRepository;
import org.jordicunillerarivera.spaceship.service.SpaceshipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SpaceshipCacheTest {
    @Autowired
    private SpaceshipService service;

    @Autowired
    private SpaceshipRepository repository;

    @Test
    void testFindByIdCacheResult() {
        Spaceship s = new Spaceship();
        s.setName("CacheShip");
        s.setModel("X");
        s.setManufacturer("Corp");
        s.setCrewCapacity(4);
        s = repository.save(s);


        // Primera llamada, se carga en caché
        SpaceshipDTO firstCall = service.findById(s.getId());


        // Borramos la BD, pero el caché mantiene la información
        repository.deleteAll();
        SpaceshipDTO cachedCall = service.findById(s.getId());


        assertEquals("CacheShip", cachedCall.name());
        assertEquals(firstCall.name(), cachedCall.name());
    }
}
