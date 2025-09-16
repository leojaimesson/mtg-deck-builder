package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.entitie.Images;
import com.mtg.deck_builder.external.scryfall.dto.ScryfallImagesDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImagesMapper {
    private static final Logger log = LoggerFactory.getLogger(ImagesMapper.class);

    public static Images toDomain(ScryfallImagesDto dto) {
        log.info("[IMAGES MAPPER] === Starting conversion of ScryfallImagesDto ===");

        if (dto == null) {
            log.info("[IMAGES MAPPER] Input DTO is null. Returning null.");
            return null;
        }

        Images images = Images.builder()
                .small(dto.getSmall())
                .normal(dto.getNormal())
                .large(dto.getLarge())
                .artCrop(dto.getArtCrop())
                .borderCrop(dto.getBorderCrop())
                .png(dto.getPng())
                .build();

        log.info("[IMAGES MAPPER] Successfully converted Images DTO to domain object:");
        log.info("[IMAGES MAPPER] small='{}', normal='{}', large='{}', artCrop='{}', borderCrop='{}', png='{}'",
                dto.getSmall(), dto.getNormal(), dto.getLarge(), dto.getArtCrop(), dto.getBorderCrop(), dto.getPng());

        log.info("[IMAGES MAPPER] === Finished conversion of ScryfallImagesDto ===");
        return images;
    }
}
