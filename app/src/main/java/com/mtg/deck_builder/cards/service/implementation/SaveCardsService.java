package com.mtg.deck_builder.cards.service.implementation;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.repository.jpa.CardRepository;
import com.mtg.deck_builder.external.scryfall.service.ScryfallService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveCardsService implements com.mtg.deck_builder.cards.service.protocol.SaveCardsService {

    private static final Logger log = LoggerFactory.getLogger(SaveCardsService.class);

    private final CardRepository cardRepository;

    public SaveCardsService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void exec(List<Card> cards) {
        log.info("[SAVE CARDS SERVICE] === Saving cards process started === Input size={}", cards.size());

        if (!cards.isEmpty()) {
            long startTime = System.currentTimeMillis();
            log.info("[SAVE CARDS SERVICE] Persisting {} new cards into DB...", cards.size());
            cardRepository.saveAll(cards);
            log.info("[SAVE CARDS SERVICE] Successfully saved {} cards. Time taken: {} ms", cards.size(), System.currentTimeMillis() - startTime);
        } else {
            log.info("[SAVE CARDS SERVICE] No new cards to save. Skipping persistence step.");
        }

        log.info("[SAVE CARDS SERVICE] === Saving cards process finished ===");
    }
}
