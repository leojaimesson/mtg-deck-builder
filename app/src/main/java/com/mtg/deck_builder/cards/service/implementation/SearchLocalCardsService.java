package com.mtg.deck_builder.cards.service.implementation;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.repository.jpa.CardRepository;
import com.mtg.deck_builder.cards.repository.specifications.CardSpecifications;
import com.mtg.deck_builder.cards.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SearchLocalCardsService implements com.mtg.deck_builder.cards.service.protocol.SearchLocalCardsService {

    private static final Logger log = LoggerFactory.getLogger(SearchLocalCardsService.class);

    private final CardRepository cardRepository;

    public SearchLocalCardsService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> exec(String query) {
        log.info("[SEARCH LOCAL CARDS SERVICE] === Starting search process ===");
        long totalStartTime = System.currentTimeMillis();

        String sanitizedQuery = StringUtil.sanitize(query);
        log.info("[SEARCH LOCAL CARDS SERVICE] Original query='{}', Sanitized query='{}'", query, sanitizedQuery);

        long startTime = System.currentTimeMillis();
        List<Card> localResults = cardRepository.findAll(CardSpecifications.searchByTermsAndPhrase(sanitizedQuery));
        long localSearchTime = System.currentTimeMillis() - startTime;

        if (!localResults.isEmpty()) {
            log.info("[SEARCH LOCAL CARDS SERVICE] Found {} cards in local DB for query='{}' (took {} ms)",
                    localResults.size(), sanitizedQuery, localSearchTime);
            log.info("[SEARCH LOCAL CARDS SERVICE] === Search process finished (local DB) === Total time: {} ms", System.currentTimeMillis() - totalStartTime);
            return localResults;
        }

        log.info("[SEARCH LOCAL CARDS SERVICE] No local results for query='{}'", sanitizedQuery);

        log.info("[SEARCH LOCAL CARDS SERVICE] === Search process finished for query='{}' === Total time: {} ms",
                sanitizedQuery, System.currentTimeMillis() - totalStartTime);

        return Collections.emptyList();
    }
}
