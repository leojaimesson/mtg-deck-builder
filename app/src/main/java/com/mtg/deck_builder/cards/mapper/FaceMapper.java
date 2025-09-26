package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.dto.response.FaceResponseDto;
import com.mtg.deck_builder.cards.entitie.Face;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FaceMapper {

    private static final Logger log = LoggerFactory.getLogger(FaceMapper.class);

    public static FaceResponseDto toFaceResponseDto(Face face) {
        if (face == null) {
            log.warn("[FACE MAPPER] Received null Face entity. Returning null DTO.");
            return null;
        }

        log.info("[FACE MAPPER] Converting Face entity with ID '{}' to FaceResponseDto", face.getId());

        try {
            FaceResponseDto dto = new FaceResponseDto(
                    face.getId(),
                    face.getName(),
                    face.getDescription(),
                    face.getType(),
                    face.getManaCost(),
                    face.getPower(),
                    face.getToughness(),
                    face.getColors(),
                    face.getArtist(),
                    ImagesMapper.toImagesResponseDto(face.getImages()),
                    face.getCreatedAt(),
                    face.getUpdatedAt()
            );

            log.info("[FACE MAPPER] Successfully converted Face with ID '{}' to FaceResponseDto", face.getId());
            return dto;

        } catch (Exception e) {
            log.error("[FACE MAPPER] Failed to convert Face with ID '{}' to FaceResponseDto", face.getId(), e);
            throw e;
        }
    }
}
