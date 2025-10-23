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
                        .title(AppConstants.TITLE)
                        .description(AppConstants.DESC)
                        .version(AppConstants.VER)
                        .contact(new Contact().name(AppConstants.CONTACT_NAME).email(AppConstants.CONTACT_EMAIL)));
    }
}
