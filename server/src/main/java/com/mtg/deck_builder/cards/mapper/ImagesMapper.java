package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.dto.response.ImagesResponseDto;
import com.mtg.deck_builder.cards.entitie.Images;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImagesMapper {

    private static final Logger log = LoggerFactory.getLogger(ImagesMapper.class);

    public static ImagesResponseDto toImagesResponseDto(Images images) {
        if (images == null) {
            log.warn("[IMAGES MAPPER] Received null Images entity. Returning null DTO.");
            return null;
        }

        log.info("[IMAGES MAPPER] Converting Images entity to ImagesResponseDto");

        try {
            ImagesResponseDto dto = new ImagesResponseDto(
                    images.getSmall(),
                    images.getNormal(),
                    images.getLarge(),
                    images.getPng(),
                    images.getArtCrop(),
                    images.getBorderCrop()
            );

            log.info("[IMAGES MAPPER] Successfully converted Images entity to ImagesResponseDto");
            return dto;

        } catch (Exception e) {
            log.error("[IMAGES MAPPER] Failed to convert Images entity to ImagesResponseDto", e);
            throw e;
        }
    }
}
