package com.mtg.deck_builder.cards.service.implementation;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.repository.jpa.CardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SaveCardsService implements com.mtg.deck_builder.cards.service.protocol.SaveCardsService {

    private static final Logger log = LoggerFactory.getLogger(SaveCardsService.class);

    private final CardRepository cardRepository;

    public SaveCardsService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public List<Card> exec(List<Card> cards) {
        log.info("[SAVE CARDS SERVICE] === Saving cards process started === Input size={}", cards.size());
        List<Card> filtered = filterNotSaved(cards);
        if (filtered.isEmpty()) {
            log.info("[SAVE CARDS SERVICE] No new cards to save. Skipping persistence step.");
            log.info("[SAVE CARDS SERVICE] === Saving cards process finished ===");
            return Collections.emptyList();
        }
        long startTime = System.currentTimeMillis();
        log.info("[SAVE CARDS SERVICE] Persisting {} new cards into DB...", cards.size());
        List<Card> storedCards = cardRepository.saveAll(filtered);
        log.info("[SAVE CARDS SERVICE] Successfully saved {} cards. Time taken: {} ms", storedCards.size(), System.currentTimeMillis() - startTime);
        log.info("[SAVE CARDS SERVICE] === Saving cards process finished ===");
        return  storedCards;
    }

    private List<Card> filterNotSaved(List<Card> cards) {
        log.info("[SAVE CARDS SERVICE] --- Filtering new cards --- Input size={}", cards.size());

        List<String> scryfallIds = cards.stream()
                .map(Card::getScryfallId)
                .filter(Objects::nonNull)
                .toList();
        log.info("[SAVE CARDS SERVICE] Extracted {} Scryfall IDs from input cards: {}", scryfallIds.size(), scryfallIds);

        Set<String> existingIds = cardRepository.findByScryfallIdIn(scryfallIds).stream()
                .map(Card::getScryfallId)
                .collect(Collectors.toSet());
        log.info("[SAVE CARDS SERVICE] Found {} existing IDs in DB: {}", existingIds.size(), existingIds);

        List<Card> notSaved = cards.stream()
                .filter(card -> card.getScryfallId() == null || !existingIds.contains(card.getScryfallId()))
                .toList();
        log.info("[SAVE CARDS SERVICE] {} cards are new and will be persisted. {} already exist.", notSaved.size(), existingIds.size());

        return notSaved;
    }
}
