package com.mtg.deck_builder.cards.service;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.repository.CardRepository;
import com.mtg.deck_builder.cards.repository.specifications.CardSpecifications;
import com.mtg.deck_builder.cards.util.StringUtil;
import com.mtg.deck_builder.external.scryfall.service.ScryfallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardService {
    private static final Logger log = LoggerFactory.getLogger(CardService.class);

    private final ScryfallService scryfallService;
    private final CardRepository cardRepository;

    public CardService(ScryfallService scryfallService, CardRepository cardRepository) {
        this.scryfallService = scryfallService;
        this.cardRepository = cardRepository;
    }

    public List<Card> searchCards(String query) {
        log.info("[CARD SERVICE] === Starting search process ===");
        long totalStartTime = System.currentTimeMillis();

        String sanitizedQuery = StringUtil.sanitize(query);
        log.info("[CARD SERVICE] Original query='{}', Sanitized query='{}'", query, sanitizedQuery);

        long startTime = System.currentTimeMillis();
        List<Card> localResults = cardRepository.findAll(CardSpecifications.searchByTermsAndPhrase(sanitizedQuery));
        long localSearchTime = System.currentTimeMillis() - startTime;

        if (!localResults.isEmpty()) {
            log.info("[CARD SERVICE] Found {} cards in local DB for query='{}' (took {} ms)",
                    localResults.size(), sanitizedQuery, localSearchTime);
            log.info("[CARD SERVICE] === Search process finished (local DB) === Total time: {} ms", System.currentTimeMillis() - totalStartTime);
            return localResults;
        }

        log.info("[CARD SERVICE] No local results for query='{}'. Querying Scryfall API...", sanitizedQuery);

        startTime = System.currentTimeMillis();
        List<Card> apiResults = scryfallService.searchCards(sanitizedQuery);
        long apiSearchTime = System.currentTimeMillis() - startTime;

        if (!apiResults.isEmpty()) {
            log.info("[CARD SERVICE] Scryfall returned {} cards for query='{}' (took {} ms). Proceeding to save.",
                    apiResults.size(), sanitizedQuery, apiSearchTime);
            saveCards(apiResults);
        } else {
            log.warn("[CARD SERVICE] No cards found on Scryfall for query='{}'. API search took {} ms", sanitizedQuery, apiSearchTime);
        }

        log.info("[CARD SERVICE] === Search process finished for query='{}' === Total time: {} ms",
                sanitizedQuery, System.currentTimeMillis() - totalStartTime);

        return apiResults;
    }

    private List<Card> filterNotSaved(List<Card> cards) {
        log.info("[CARD SERVICE] --- Filtering new cards --- Input size={}", cards.size());

        List<String> scryfallIds = cards.stream()
                .map(Card::getScryfallId)
                .filter(Objects::nonNull)
                .toList();
        log.info("[CARD SERVICE] Extracted {} Scryfall IDs from input cards: {}", scryfallIds.size(), scryfallIds);

        Set<String> existingIds = cardRepository.findByScryfallIdIn(scryfallIds).stream()
                .map(Card::getScryfallId)
                .collect(Collectors.toSet());
        log.info("[CARD SERVICE] Found {} existing IDs in DB: {}", existingIds.size(), existingIds);

        List<Card> notSaved = cards.stream()
                .filter(card -> card.getScryfallId() == null || !existingIds.contains(card.getScryfallId()))
                .toList();
        log.info("[CARD SERVICE] {} cards are new and will be persisted. {} already exist.", notSaved.size(), existingIds.size());

        return notSaved;
    }

    private void saveCards(List<Card> cards) {
        log.info("[CARD SERVICE] === Saving cards process started === Input size={}", cards.size());

        List<Card> filtered = filterNotSaved(cards);
        if (!filtered.isEmpty()) {
            long startTime = System.currentTimeMillis();
            log.info("[CARD SERVICE] Persisting {} new cards into DB...", filtered.size());
            cardRepository.saveAll(filtered);
            log.info("[CARD SERVICE] Successfully saved {} cards. Time taken: {} ms", filtered.size(), System.currentTimeMillis() - startTime);
        } else {
            log.info("[CARD SERVICE] No new cards to save. Skipping persistence step.");
        }

        log.info("[CARD SERVICE] === Saving cards process finished ===");
    }
}
