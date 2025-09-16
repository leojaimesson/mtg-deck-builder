package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.util.HashUtil;
import com.mtg.deck_builder.external.scryfall.dto.ScryfallCardDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CardMapper {

    private static final Logger log = LoggerFactory.getLogger(CardMapper.class);

    public static Card fromScryfallCardDtoToDomain(ScryfallCardDto dto) {
        String generatedId = HashUtil.generateId();
        log.info("[CARD MAPPER] Converting DTO '{}' to Card with generated ID '{}'", dto.getName(), generatedId);

        Card card = Card.builder()
                .id(generatedId)
                .scryfallId(dto.getId())
                .name(dto.getName())
                .type(dto.getTypeLine())
                .description(dto.getOracleText())
                .rarity(dto.getRarity())
                .artist(dto.getArtist())
                .manaCost(dto.getManaCost())
                .totalManaCost(dto.getCmc())
                .power(dto.getPower())
                .toughness(dto.getToughness())
                .colors(dto.getColors())
                .colorIdentity(dto.getColorIdentity())
                .faces(FaceMapper.fromScryfallFaceDtoToDomainList(dto.getFaces()))
                .images(ImagesMapper.toDomain(dto.getImages()))
                .build();

        log.info("[CARD MAPPER] Successfully converted DTO '{}' to Card with ID '{}'", dto.getName(), card.getId());
        return card;
    }

    public static List<Card> fromScryfallCardDtoToDomainList(List<ScryfallCardDto> cards) {
        log.info("[CARD MAPPER] === Starting conversion of {} DTOs to domain Cards ===", cards == null ? 0 : cards.size());

        if (cards == null || cards.isEmpty()) {
            log.info("[CARD MAPPER] No DTOs provided. Returning empty list.");
            return Collections.emptyList();
        }

        List<Card> domainCards = cards.stream()
                .map(CardMapper::fromScryfallCardDtoToDomain)
                .collect(Collectors.toList());

        log.info("[CARD MAPPER] === Finished conversion. {} Cards created ===", domainCards.size());
        return domainCards;
    }
}
