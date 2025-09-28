package com.mtg.deck_builder.cards.service.protocol;

import com.mtg.deck_builder.cards.entitie.Card;

import java.util.List;

public interface SearchScryfallCardsService {
    public List<Card> exec(String query);
}
