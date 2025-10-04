package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.dto.response.CardResponseDto;
import com.mtg.deck_builder.cards.entitie.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CardMapper {

    private static final Logger log = LoggerFactory.getLogger(CardMapper.class);

    public static CardResponseDto toResponseDto(Card card) {
        if (card == null) {
            log.warn("[CARD MAPPER] Received null Card entity. Returning null DTO.");
            return null;
        }

        log.info("[CARD MAPPER] Converting Card entity with ID '{}' to CardResponseDto", card.getId());

        try {
            CardResponseDto dto = new CardResponseDto(
                    card.getId(),
                    card.getScryfallId(),
                    card.getName(),
                    card.getDescription(),
                    card.getType(),
                    card.getManaCost(),
                    card.getTotalManaCost(),
                    card.getPower(),
                    card.getToughness(),
                    card.getColors(),
                    card.getColorIdentity(),
                    card.getRarity(),
                    card.getArtist(),
                    ImagesMapper.toImagesResponseDto(card.getImages()),
                    card.getFaces() != null
                            ? card.getFaces().stream().map(FaceMapper::toFaceResponseDto).toList()
                            : List.of(),
                    card.getCreatedAt(),
                    card.getUpdatedAt()
            );

            log.info("[CARD MAPPER] Successfully converted Card with ID '{}' to CardResponseDto", card.getId());
            return dto;

        } catch (Exception e) {
            log.error("[CARD MAPPER] Failed to convert Card with ID '{}' to CardResponseDto", card.getId(), e);
            throw e;
        }
    }
}
