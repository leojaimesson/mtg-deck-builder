package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.entitie.Face;
import com.mtg.deck_builder.cards.util.HashUtil;
import com.mtg.deck_builder.external.scryfall.dto.ScryfallFaceDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FaceMapper {
    public static Face fromScryfallFaceDtoToDomain(ScryfallFaceDto dto) {
        return Face.builder()
                .id(HashUtil.generateId())
                .name(dto.getName())
                .type(dto.getTypeLine())
                .description(dto.getOracleText())
                .artist(dto.getArtist())
                .manaCost(dto.getManaCost())
                .power(dto.getPower())
                .toughness(dto.getToughness())
                .colors(dto.getColors())
                .images(ImagesMapper.toDomain(dto.getImages()))
                .build();
    }

    public static List<Face> fromScryfallFaceDtoToDomainList(List<ScryfallFaceDto> dtoList) {
        if (dtoList == null) {
            return Collections.emptyList();
        }
        return dtoList.stream()
                .map(FaceMapper::fromScryfallFaceDtoToDomain)
                .collect(Collectors.toList());
    }


}
