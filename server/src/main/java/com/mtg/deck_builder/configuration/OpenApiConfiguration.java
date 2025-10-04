package com.mtg.deck_builder.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MTG DeckBuilder API")
                        .version("0.0.1")
                        .description("DeckBuilder API is a service for searching, registering, and managing Magic The Gathering cards.\n  The system allows users to search for cards, save them in the local database, and build custom decks.")
                );
    }
}

