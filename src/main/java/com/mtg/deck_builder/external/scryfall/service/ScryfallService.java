package com.mtg.deck_builder.external.scryfall.service;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.mapper.CardMapper;
import com.mtg.deck_builder.external.scryfall.client.ScryfallClient;
import com.mtg.deck_builder.external.scryfall.dto.ScryfallCardDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScryfallService {
    private static final Logger log = LoggerFactory.getLogger(ScryfallService.class);

    private final ScryfallClient client;

    public ScryfallService(ScryfallClient client) {
        this.client = client;
    }

    public List<Card> searchCards(String query) {
        log.info("[SCRYFALL SERVICE] === Starting ScryfallService search ===");
        log.info("[SCRYFALL SERVICE] Query: '{}'", query);
        long startTime = System.currentTimeMillis();

        // Fetch from ScryfallClient
        List<ScryfallCardDto> dtoList = client.searchCards(query);
        log.info("[SCRYFALL SERVICE] Retrieved {} cards from ScryfallClient", dtoList.size());

        // Map DTOs to domain
        List<Card> domainCards = CardMapper.fromScryfallCardDtoToDomainList(dtoList);
        log.info("[SCRYFALL SERVICE] Converted {} DTOs to domain Card objects. Time taken: {} ms",
                domainCards.size(), System.currentTimeMillis() - startTime);

        log.info("[SCRYFALL SERVICE] === Finished ScryfallService search ===");
        return domainCards;
    }
}
