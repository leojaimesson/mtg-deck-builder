package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.entitie.Face;
import com.mtg.deck_builder.cards.persistence.entitie.FaceEntity;
import com.mtg.deck_builder.external.scryfall.dto.ScryfallFaceDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FaceMapper {
    public static Face toDomain(ScryfallFaceDto dto) {
        return Face.builder()
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

    public static List<Face> toDomainList(List<ScryfallFaceDto> dtoList) {
        if (dtoList == null) {
            return Collections.emptyList();
        }
        return dtoList.stream()
                .map(FaceMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static Face fromEntityToDomain(FaceEntity entity) {
        if (entity == null) {
            return null;
        }
        return Face.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .type(entity.getType())
                .manaCost(entity.getManaCost())
                .power(entity.getPower())
                .toughness(entity.getToughness())
                .artist(entity.getArtist())
                .colors(entity.getColors())
                .images(entity.getImages() != null ? ImagesMapper.fromEmbeddableToDomain(entity.getImages()) : null)
                .build();
    }

}
