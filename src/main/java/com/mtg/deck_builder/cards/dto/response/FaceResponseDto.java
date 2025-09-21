package com.mtg.deck_builder.cards.dto.response;

import java.time.Instant;
import java.util.List;

public record FaceResponseDto(
        String id,
        String name,
        String description,
        String type,
        String manaCost,
        String power,
        String toughness,
        List<String> colors,
        String artist,
        ImagesResponseDto images,
        Instant createdAt,
        Instant updatedAt
) {}
