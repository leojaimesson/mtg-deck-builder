package com.mtg.deck_builder.cards.controller;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.service.CardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }

    @GetMapping()
    public List<Card> getCardById(@RequestParam String query) {
        return service.searchCards(query);
    }
}
