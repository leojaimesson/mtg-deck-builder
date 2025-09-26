package com.mtg.deck_builder.cards.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(name = "Face", description = "Represents one face of a double-faced or modal card")
public record FaceResponseDto(

        @Schema(description = "Unique identifier of the card face", example = "987e6543-e21b-12d3-a456-426614174999")
        String id,

        @Schema(description = "Face name", example = "Fire")
        String name,

        @Schema(description = "Face description or oracle text", example = "Fire deals 2 damage divided as you choose among any number of targets.")
        String description,

        @Schema(description = "Type line of the face", example = "Instant")
        String type,

        @Schema(description = "Mana cost string", example = "{1}{R}")
        String manaCost,

        @Schema(description = "Power value (if applicable)", example = "2")
        String power,

        @Schema(description = "Toughness value (if applicable)", example = "2")
        String toughness,

        @Schema(description = "List of colors", example = "[\"R\"]")
        List<String> colors,

        @Schema(description = "Artist of the card illustration", example = "Kev Walker")
        String artist,

        @Schema(description = "Images for the card face")
        ImagesResponseDto images,

        @Schema(description = "Creation timestamp", example = "2025-09-22T14:30:00Z")
        Instant createdAt,

        @Schema(description = "Last update timestamp", example = "2025-09-22T15:45:00Z")
        Instant updatedAt
) {}
