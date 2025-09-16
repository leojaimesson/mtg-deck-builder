package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.entitie.Images;
import com.mtg.deck_builder.cards.persistence.entitie.ImagesEmbeddable;
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

    public static Images fromEmbeddableToDomain(ImagesEmbeddable embeddable) {
        if (embeddable == null) {
            return null;
        }
        return Images.builder()
                .small(embeddable.getSmall())
                .normal(embeddable.getNormal())
                .large(embeddable.getLarge())
                .png(embeddable.getPng())
                .artCrop(embeddable.getArtCrop())
                .borderCrop(embeddable.getBorderCrop())
                .build();
    }

}
