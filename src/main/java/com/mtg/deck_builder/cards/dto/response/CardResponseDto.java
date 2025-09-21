package com.mtg.deck_builder.cards.dto.response;

import java.time.Instant;
import java.util.List;

public record CardResponseDto (
        String id,
        String scryfallId,
        String name,
        String description,
        String type,
        String manaCost,
        String totalManaCost,
        String power,
        String toughness,
        List<String> colors,
        List<String> colorIdentity,
        String rarity,
        String artist,
        ImagesResponseDto images,
        List<FaceResponseDto> faces,
        Instant createdAt,
        Instant updatedAt
) {}
