package com.mtg.deck_builder.cards.service.implementation;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.repository.jpa.CardRepository;
import com.mtg.deck_builder.cards.repository.specifications.CardSpecifications;
import com.mtg.deck_builder.cards.service.protocol.SaveCardsService;
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
public class SearchScryfallCardsService implements com.mtg.deck_builder.cards.service.protocol.SearchScryfallCardsService {

    private static final Logger log = LoggerFactory.getLogger(SearchScryfallCardsService.class);

    private final ScryfallService scryfallService;
    private final CardRepository cardRepository;
    private final SaveCardsService saveCardsService;

    public SearchScryfallCardsService(ScryfallService scryfallService, CardRepository cardRepository, SaveCardsService saveCardsService) {
        this.scryfallService = scryfallService;
        this.cardRepository = cardRepository;
        this.saveCardsService = saveCardsService;
    }

    @Override
    public List<Card> exec(String query) {
        log.info("[SEARCH SCRYFALL CARDS SERVICE] === Starting search process ===");
        long totalStartTime = System.currentTimeMillis();

        String sanitizedQuery = StringUtil.sanitize(query);
        log.info("[SEARCH SCRYFALL CARDS SERVICE] Original query='{}', Sanitized query='{}'", query, sanitizedQuery);

        long startTime = System.currentTimeMillis();

        List<Card> apiResults = scryfallService.searchCards(sanitizedQuery);

        long apiSearchTime = System.currentTimeMillis() - startTime;

        if (!apiResults.isEmpty()) {
            log.info("[SEARCH SCRYFALL CARDS SERVICE] Scryfall returned {} cards for query='{}' (took {} ms). Proceeding to save.",
                    apiResults.size(), sanitizedQuery, apiSearchTime);
            saveCardsService.exec(filterNotSaved(apiResults));
        } else {
            log.warn("[SEARCH SCRYFALL CARDS SERVICE] No cards found on Scryfall for query='{}'. API search took {} ms", sanitizedQuery, apiSearchTime);
        }

        log.info("[SEARCH SCRYFALL CARDS SERVICE] === Search process finished for query='{}' === Total time: {} ms",
                sanitizedQuery, System.currentTimeMillis() - totalStartTime);

        return apiResults;
    }

    private List<Card> filterNotSaved(List<Card> cards) {
        log.info("[SEARCH SCRYFALL CARDS SERVICE] --- Filtering new cards --- Input size={}", cards.size());

        List<String> scryfallIds = cards.stream()
                .map(Card::getScryfallId)
                .filter(Objects::nonNull)
                .toList();
        log.info("[SEARCH SCRYFALL CARDS SERVICE] Extracted {} Scryfall IDs from input cards: {}", scryfallIds.size(), scryfallIds);

        Set<String> existingIds = cardRepository.findByScryfallIdIn(scryfallIds).stream()
                .map(Card::getScryfallId)
                .collect(Collectors.toSet());
        log.info("[SEARCH SCRYFALL CARDS SERVICE] Found {} existing IDs in DB: {}", existingIds.size(), existingIds);

        List<Card> notSaved = cards.stream()
                .filter(card -> card.getScryfallId() == null || !existingIds.contains(card.getScryfallId()))
                .toList();
        log.info("[SEARCH SCRYFALL CARDS SERVICE] {} cards are new and will be persisted. {} already exist.", notSaved.size(), existingIds.size());

        return notSaved;
    }
}
