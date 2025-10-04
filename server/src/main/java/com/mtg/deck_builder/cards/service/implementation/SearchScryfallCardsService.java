package com.mtg.deck_builder.cards.service.implementation;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.util.StringUtil;
import com.mtg.deck_builder.external.scryfall.service.ScryfallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchScryfallCardsService implements com.mtg.deck_builder.cards.service.protocol.SearchScryfallCardsService {

    private static final Logger log = LoggerFactory.getLogger(SearchScryfallCardsService.class);

    private final ScryfallService scryfallService;

    public SearchScryfallCardsService(ScryfallService scryfallService) {
        this.scryfallService = scryfallService;
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
        } else {
            log.warn("[SEARCH SCRYFALL CARDS SERVICE] No cards found on Scryfall for query='{}'. API search took {} ms", sanitizedQuery, apiSearchTime);
        }

        log.info("[SEARCH SCRYFALL CARDS SERVICE] === Search process finished for query='{}' === Total time: {} ms",
                sanitizedQuery, System.currentTimeMillis() - totalStartTime);

        return apiResults;
    }
}
