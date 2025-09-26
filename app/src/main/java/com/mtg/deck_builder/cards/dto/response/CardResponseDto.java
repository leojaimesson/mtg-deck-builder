package com.mtg.deck_builder.cards.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(name = "Card", description = "Represents a Magic The Gathering card")
public record CardResponseDto(

        @Schema(description = "Unique identifier of the card", example = "123e4567-e89b-12d3-a456-426614174000")
        String id,

        @Schema(description = "Scryfall identifier for the card", example = "4c1e6a12-2a3e-4e71-90e1-5c5a4a9e1b99")
        String scryfallId,

        @Schema(description = "Card name", example = "Lightning Bolt")
        String name,

        @Schema(description = "Card description or oracle text", example = "Lightning Bolt deals 3 damage to any target.")
        String description,

        @Schema(description = "Type line of the card", example = "Instant")
        String type,

        @Schema(description = "Mana cost string", example = "{R}")
        String manaCost,

        @Schema(description = "Total converted mana cost", example = "1")
        String totalManaCost,

        @Schema(description = "Power value (if applicable)", example = "3")
        String power,

        @Schema(description = "Toughness value (if applicable)", example = "2")
        String toughness,

        @Schema(description = "List of colors", example = "[\"R\"]")
        List<String> colors,

        @Schema(description = "Color identity", example = "[\"R\"]")
        List<String> colorIdentity,

        @Schema(description = "Card rarity", example = "Common")
        String rarity,

        @Schema(description = "Artist of the card illustration", example = "Christopher Rush")
        String artist,

        @Schema(description = "Card image variations")
        ImagesResponseDto images,

        @Schema(description = "Different faces of the card if it is double-faced or modal")
        List<FaceResponseDto> faces,

        @Schema(description = "Creation timestamp", example = "2025-09-22T14:30:00Z")
        Instant createdAt,

        @Schema(description = "Last update timestamp", example = "2025-09-22T15:45:00Z")
        Instant updatedAt
) {}
