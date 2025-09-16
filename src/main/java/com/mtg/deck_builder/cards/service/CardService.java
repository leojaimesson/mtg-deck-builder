package com.mtg.deck_builder.cards.service;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.mapper.CardMapper;
import com.mtg.deck_builder.cards.persistence.entitie.CardEntity;
import com.mtg.deck_builder.cards.persistence.repository.CardRepository;
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
        log.info("[CARD SERVICE] - Starting search with query='{}'", query);

        long startTime = System.currentTimeMillis();
        List<CardEntity> localResults = cardRepository.searchByNameOrDescription(query);

        if (!localResults.isEmpty()) {
            log.info("[CARD SERVICE] - Found {} cards in local DB for query='{}' (took {} ms)",
                    localResults.size(), query, System.currentTimeMillis() - startTime);
            return CardMapper.fromCardEntityToDomainList(localResults);
        }

        log.info("[CARD SERVICE] - No local results for query='{}'. Querying Scryfall API...", query);
        List<Card> list = scryfallService.searchCards(query);

        if (!list.isEmpty()) {
            log.info("[CARD SERVICE] - Scryfall returned {} cards for query='{}'. Proceeding to save.",
                    list.size(), query);
            saveCards(list);
        } else {
            log.warn("[CARD SERVICE] - No cards found on Scryfall for query='{}'", query);
        }

        log.info("[CARD SERVICE] - Finished search for query='{}' in {} ms", query, System.currentTimeMillis() - startTime);
        return list;
    }

    private List<Card> filterNotSaved(List<Card> cards) {
        log.debug("[CARD SERVICE] - Filtering not-saved cards. Input size={}", cards.size());

        List<String> scryfallIds = cards.stream()
                .map(Card::getScryfallId)
                .filter(Objects::nonNull)
                .toList();

        log.debug("[CARD SERVICE] - Extracted {} Scryfall IDs from input cards.", scryfallIds.size());

        Set<String> existingIds = cardRepository.findByScryfallIdIn(scryfallIds).stream()
                .map(CardEntity::getScryfallId)
                .collect(Collectors.toSet());

        log.debug("[CARD SERVICE] - Found {} existing IDs in DB.", existingIds.size());

        List<Card> notSaved = cards.stream()
                .filter(card -> card.getScryfallId() == null || !existingIds.contains(card.getScryfallId()))
                .toList();

        log.info("[CARD SERVICE] - {} cards are new and will be persisted. {} already exist.",
                notSaved.size(), existingIds.size());

        return notSaved;
    }

    private void saveCards(List<Card> cards) {
        log.info("[CARD SERVICE] - Preparing to save {} cards.", cards.size());

        List<Card> filtered = filterNotSaved(cards);
        if (!filtered.isEmpty()) {
            log.info("[CARD SERVICE] - Persisting {} new cards into DB.", filtered.size());
            cardRepository.saveAll(CardMapper.toEntityList(filtered));
            log.info("[CARD SERVICE] - Successfully saved {} cards.", filtered.size());
        } else {
            log.info("[CARD SERVICE] - No new cards to save. Skipping persistence step.");
        }
    }
}
