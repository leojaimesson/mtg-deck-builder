package com.mtg.deck_builder.cards.controller;

import com.mtg.deck_builder.cards.dto.response.CardResponseDto;
import com.mtg.deck_builder.cards.mapper.CardMapper;
import com.mtg.deck_builder.cards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@Tag(name = "Cards", description = "Endpoints related to Magic: The Gathering cards")
public class CardController {

    private static final Logger log = LoggerFactory.getLogger(CardController.class);

    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
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
            @RequestParam String query
    ) {
        log.info("[CARD CONTROLLER] Received request to search cards with query='{}'", query);

        try {
            List<CardResponseDto> results = service.searchCards(query).stream()
                    .map(CardMapper::toResponseDto)
                    .toList();

            log.info("[CARD CONTROLLER] Successfully retrieved {} cards for query='{}'", results.size(), query);

            return results;

        } catch (Exception e) {
            log.error("[CARD CONTROLLER] Error occurred while searching cards with query='{}'", query, e);
            throw e;
        }
    }
}
