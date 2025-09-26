package com.mtg.deck_builder.external.scryfall.client;

import com.mtg.deck_builder.external.scryfall.dto.ScryfallCardDto;
import com.mtg.deck_builder.external.scryfall.dto.ScryfallSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class ScryfallClient {
    private static final Logger log = LoggerFactory.getLogger(ScryfallClient.class);
    private final WebClient webClient;

    public ScryfallClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.scryfall.com")
                .defaultHeader("User-Agent", "DeckBuilderApp/1.0")
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(50 * 1024 * 1024))
                .build();
    }

    public List<ScryfallCardDto> searchCards(String query) {
        log.info("[SCRYFALL CLIENT] === Starting search on Scryfall API ===");
        log.info("[SCRYFALL CLIENT] Query: '{}'", query);
        long startTime = System.currentTimeMillis();

        try {
            ScryfallSearchResponse response = webClient.get()
                    .uri("/cards/search?q={query}", query)
                    .retrieve()
                    .bodyToMono(ScryfallSearchResponse.class)
                    .block();

            if (response == null || response.getData() == null || response.getData().isEmpty()) {
                log.warn("[SCRYFALL CLIENT] No cards found for query='{}'. Time taken: {} ms", query, System.currentTimeMillis() - startTime);
                return List.of();
            }

            log.info("[SCRYFALL CLIENT] Found {} cards for query='{}'. Time taken: {} ms", response.getData().size(), query, System.currentTimeMillis() - startTime);
            log.info("[SCRYFALL CLIENT] === Finished search on Scryfall API ===");
            return response.getData();

        } catch (org.springframework.web.reactive.function.client.WebClientResponseException.NotFound e) {
            log.warn("[SCRYFALL CLIENT] No cards found (404) for query='{}'. Details: {}. Time taken: {} ms",
                    query, e.getResponseBodyAsString(), System.currentTimeMillis() - startTime);
            return List.of();
        } catch (Exception e) {
            log.error("[SCRYFALL CLIENT] Error while querying Scryfall API for query='{}'. Cause: {}. Time taken: {} ms",
                    query, e.getMessage(), System.currentTimeMillis() - startTime, e);
            return List.of();
        }
    }
}
