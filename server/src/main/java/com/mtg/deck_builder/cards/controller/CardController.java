package com.mtg.deck_builder.cards.controller;

import com.mtg.deck_builder.cards.dto.response.CardResponseDto;
import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.mapper.CardMapper;
import com.mtg.deck_builder.cards.queue.producer.CardProducerService;
import com.mtg.deck_builder.cards.service.protocol.SaveCardsService;
import com.mtg.deck_builder.cards.service.protocol.SearchLocalCardsService;
import com.mtg.deck_builder.cards.service.protocol.SearchScryfallCardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/cards")
@Tag(name = "Cards", description = "Endpoints related to Magic: The Gathering cards")
public class CardController {
    private static final Logger log = LoggerFactory.getLogger(CardController.class);

    private final SearchLocalCardsService searchLocalCardsService;
    private final SearchScryfallCardsService searchScryfallCardsService;
    private final CardProducerService cardProducerService;

    public CardController(SearchLocalCardsService searchLocalCardsService, SearchScryfallCardsService searchScryfallCardsService, CardProducerService cardProducerService) {
        this.searchLocalCardsService = searchLocalCardsService;
        this.searchScryfallCardsService = searchScryfallCardsService;
        this.cardProducerService = cardProducerService;
    }

    @GetMapping
    @Operation(
            summary = "Search cards",
            description = "Search Magic The Gathering cards by full or partial name."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards successfully retrieved"),
    })
    public List<CardResponseDto> getCards(
            @Parameter(
                    description = "Search query for the card (full or partial name).",
                    example = "Lightning Bolt"
            )
            @RequestParam String query,
            @Parameter(
                    description = "If true, also fetches cards from Scryfall even if found locally.",
                    example = "false"
            )
            @RequestParam(required = false, defaultValue = "false") boolean searchNewCards
    ) {
        log.info("[CARD CONTROLLER] Received request to search cards with query='{}', searchNewCards={}", query, searchNewCards);

        try {
            List<Card> localCards = searchLocalCardsService.exec(query);

            List<Card> scryfallCards = (searchNewCards || localCards.isEmpty())
                    ? searchScryfallCardsService.exec(query)
                    : List.of();

            Map<String, Card> cardMap = new HashMap<>();

            if (!localCards.isEmpty()) {
                localCards.forEach(card -> cardMap.put(card.getScryfallId().toLowerCase(), card));
            }
            if (!scryfallCards.isEmpty()) {
                cardProducerService.sendCardMessage(scryfallCards);
                scryfallCards.forEach(card -> cardMap.put(card.getScryfallId().toLowerCase(), card));
            }

            List<Card> finalCards = new ArrayList<>(cardMap.values());

            List<CardResponseDto> response = finalCards
                        .stream()
                        .map(CardMapper::toResponseDto)
                        .toList();

            log.info("[CARD CONTROLLER] Successfully retrieved {} cards for query='{}'", response.size(), query);

            return response;

        } catch (Exception e) {
            log.error("[CARD CONTROLLER] Error occurred while searching cards with query='{}'", query, e);
            throw e;
        }
    }

}
