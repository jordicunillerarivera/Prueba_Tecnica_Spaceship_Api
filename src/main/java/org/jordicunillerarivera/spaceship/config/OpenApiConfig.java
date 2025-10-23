package org.jordicunillerarivera.spaceship.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spaceship API")
                        .description("CRUD de naves espaciales de series y peliculas")
                        .version("1.0.0")
                        .contact(new Contact().name("Jordi Cunillera Rivera").email("jordicunillera1@gmail.com")));
    }
}
