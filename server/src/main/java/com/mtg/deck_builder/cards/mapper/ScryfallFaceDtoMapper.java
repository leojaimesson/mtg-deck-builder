package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.entitie.Face;
import com.mtg.deck_builder.cards.util.HashUtil;
import com.mtg.deck_builder.external.scryfall.dto.ScryfallFaceDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ScryfallFaceDtoMapper {
    private static final Logger log = LoggerFactory.getLogger(ScryfallFaceDtoMapper.class);

    public static Face toDomain(ScryfallFaceDto dto) {
        String generatedId = HashUtil.generateId();
        log.info("[FACE MAPPER] Converting Face DTO '{}' (type='{}') with generated ID '{}'",
                dto.getName(), dto.getTypeLine(), generatedId);
        Instant now = Instant.now();
        Face face = Face.builder()
                .id(generatedId)
                .name(dto.getName())
                .type(dto.getTypeLine())
                .description(dto.getOracleText())
                .artist(dto.getArtist())
                .manaCost(dto.getManaCost())
                .power(dto.getPower())
                .toughness(dto.getToughness())
                .colors(dto.getColors())
                .images(ScryfallImagesDtoMapper.toDomain(dto.getImages()))
                .creadetAt(now)
                .updatedAt(now)
                .build();

        log.info("[FACE MAPPER] Successfully converted Face DTO '{}' to domain Face with ID '{}'",
                dto.getName(), face.getId());
        return face;
    }

    public static List<Face> toDomainList(List<ScryfallFaceDto> dtoList) {
        log.info("[FACE MAPPER] === Starting conversion of {} Face DTOs to domain Faces ===",
                dtoList == null ? 0 : dtoList.size());

        if (dtoList == null || dtoList.isEmpty()) {
            log.info("[FACE MAPPER] No Face DTOs provided. Returning empty list.");
            return Collections.emptyList();
        }

        List<Face> faces = dtoList.stream()
                .map(ScryfallFaceDtoMapper::toDomain)
                .collect(Collectors.toList());

        log.info("[FACE MAPPER] === Finished conversion. {} Faces created ===", faces.size());
        return faces;
    }
}
