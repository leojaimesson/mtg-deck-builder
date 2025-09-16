package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.util.HashUtil;
import com.mtg.deck_builder.external.scryfall.dto.ScryfallCardDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CardMapper {

    public static Card fromScryfallCardDtoToDomain(ScryfallCardDto dto) {
        return Card.builder()
                .id(HashUtil.generateId())
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
    }

    public static List<Card> fromScryfallCardDtoToDomainList(List<ScryfallCardDto> cards) {
        if (cards == null) {
            return Collections.emptyList();
        }
        return cards.stream()
                .map(CardMapper::fromScryfallCardDtoToDomain)
                .collect(Collectors.toList());
    }
}
