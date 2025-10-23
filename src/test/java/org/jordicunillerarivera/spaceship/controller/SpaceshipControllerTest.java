package org.jordicunillerarivera.spaceship.controller;

import org.jordicunillerarivera.spaceship.dto.CreateSpaceshipDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jordicunillerarivera.spaceship.model.Spaceship;
import org.jordicunillerarivera.spaceship.repository.SpaceshipRepository;
import org.jordicunillerarivera.spaceship.service.kafka.SpaceshipEventProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class SpaceshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceshipEventProducer eventProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpaceshipRepository repository;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
        repository.deleteAll();

        Spaceship ship = new Spaceship();
        ship.setName("TestShip");
        ship.setModel("T-1000");
        ship.setManufacturer("TestCorp");
        ship.setCrewCapacity(3);
        repository.save(ship);
    }

    @Test
    void shouldReturnAllSpaceships() throws Exception {
        mockMvc.perform(get("/api/spaceships"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldValidateOnCreate() throws Exception {
        var invalid = new CreateSpaceshipDTO("", null, null, -1);
        mockMvc.perform(post("/api/spaceships")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSearchByName() throws Exception {
        mockMvc.perform(get("/api/spaceships/search")
                .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("TestShip"));
    }

    @Test
    void testCreate() throws Exception {
        CreateSpaceshipDTO dto = new CreateSpaceshipDTO("NewShip", "X1", "NewCorp", 5);
        mockMvc.perform(post("/api/spaceships")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("NewShip"))
                .andExpect(jsonPath("$.crewCapacity").value(5));
    }

    @Test
    void testUpdate() throws Exception {
        Spaceship existing = repository.findAll().get(0);
        CreateSpaceshipDTO dto = new CreateSpaceshipDTO("UpdatedShip", "X2", "UpdatedCorp", 4);

        mockMvc.perform(put("/api/spaceships/{id}", existing.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedShip"))
                .andExpect(jsonPath("$.crewCapacity").value(4));
    }

    @Test
    void testDelete() throws Exception {
        Spaceship existing = repository.findAll().get(0);

        mockMvc.perform(delete("/api/spaceships/{id}", existing.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/spaceships/{id}", existing.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetById() throws Exception {
        Spaceship existing = repository.findAll().get(0);

        mockMvc.perform(get("/api/spaceships/{id}", existing.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(existing.getName()));
    }
}
