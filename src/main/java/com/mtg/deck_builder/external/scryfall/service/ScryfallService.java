package com.mtg.deck_builder.external.scryfall.service;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.mapper.CardMapper;
import com.mtg.deck_builder.external.scryfall.client.ScryfallClient;
import com.mtg.deck_builder.external.scryfall.dto.ScryfallCardDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScryfallService {
    private final ScryfallClient client;

    public ScryfallService(ScryfallClient client) {
        this.client = client;
    }

    public List<Card> searchCards(String query) {
        List<ScryfallCardDto> dto = client.searchCards(query);
        return CardMapper.toDomainList(dto);
    }
}
