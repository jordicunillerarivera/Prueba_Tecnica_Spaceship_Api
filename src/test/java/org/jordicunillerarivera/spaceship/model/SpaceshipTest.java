package org.jordicunillerarivera.spaceship.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SpaceshipTest {

    @Test
    void testAllArgsConstructorAndCreateAt() {
        LocalDateTime now = LocalDateTime.now();
        Spaceship ship = new Spaceship(1L, "X", "T", "I", 1, now);

        assertEquals(1L, ship.getId());
        assertEquals("X", ship.getName());
        assertEquals("T", ship.getModel());
        assertEquals("I", ship.getManufacturer());
        assertEquals(1, ship.getCrewCapacity());
        assertEquals(now, ship.getCreateAt());

        LocalDateTime newTime = now.plusDays(1);
        ship.setCreateAt(newTime);
        assertEquals(newTime, ship.getCreateAt());
    }
}
