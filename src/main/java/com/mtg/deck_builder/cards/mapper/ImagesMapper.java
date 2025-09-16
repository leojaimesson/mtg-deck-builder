package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.entitie.Images;
import com.mtg.deck_builder.external.scryfall.dto.ScryfallImagesDto;


public class ImagesMapper {
    public static Images toDomain(ScryfallImagesDto dto) {
        if (dto != null) {
            return Images.builder()
                    .small(dto.getSmall())
                    .normal(dto.getNormal())
                    .large(dto.getLarge())
                    .artCrop(dto.getArtCrop())
                    .borderCrop(dto.getBorderCrop())
                    .png(dto.getPng())
                    .build();
        }
        return null;
    }
}
