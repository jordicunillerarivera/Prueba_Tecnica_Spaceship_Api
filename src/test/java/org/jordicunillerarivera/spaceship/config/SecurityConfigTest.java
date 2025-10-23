package org.jordicunillerarivera.spaceship.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testUnauthorizedRequest() throws Exception {
        mockMvc.perform(get("/api/spaceships"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAuthorizedRequest() throws Exception {
        mockMvc.perform(get("/api/spaceships")
                .with(httpBasic("admin","password")))
                .andExpect(status().isOk());
    }
}
