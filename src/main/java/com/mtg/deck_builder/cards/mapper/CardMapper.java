package com.mtg.deck_builder.cards.mapper;

import com.mtg.deck_builder.cards.entitie.Card;
import com.mtg.deck_builder.cards.persistence.entitie.CardEntity;
import com.mtg.deck_builder.cards.persistence.entitie.FaceEntity;
import com.mtg.deck_builder.cards.persistence.entitie.ImagesEmbeddable;
import com.mtg.deck_builder.cards.util.HashUtil;
import com.mtg.deck_builder.external.scryfall.dto.ScryfallCardDto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CardMapper {

    public static Card toDomain(ScryfallCardDto dto) {
        return Card.builder()
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
                .faces(FaceMapper.toDomainList(dto.getFaces()))
                .images(ImagesMapper.toDomain(dto.getImages()))
                .build();
    }

    public static CardEntity toEntity(Card card) {
        CardEntity entity = new CardEntity();
        if (card.getId() == null) {
            entity.setId(HashUtil.generateId());
        } else {
            entity.setId(card.getId());
        }
        entity.setScryfallId(card.getScryfallId());
        entity.setName(card.getName());
        entity.setDescription(card.getDescription());
        entity.setType(card.getType());
        entity.setManaCost(card.getManaCost());
        entity.setTotalManaCost(card.getTotalManaCost());
        entity.setPower(card.getPower());
        entity.setToughness(card.getToughness());
        entity.setRarity(card.getRarity());
        entity.setArtist(card.getArtist());
        entity.setColors(card.getColors());
        entity.setColorIdentity(card.getColorIdentity());

        if (card.getImages() != null) {
            ImagesEmbeddable img = new ImagesEmbeddable();
            img.setSmall(card.getImages().getSmall());
            img.setNormal(card.getImages().getNormal());
            img.setLarge(card.getImages().getLarge());
            img.setPng(card.getImages().getPng());
            img.setArtCrop(card.getImages().getArtCrop());
            img.setBorderCrop(card.getImages().getBorderCrop());
            entity.setImages(img);
        }

        if (card.getFaces() != null) {
            entity.setFaces(card.getFaces().stream().map(face -> {
                FaceEntity f = new FaceEntity();
                if (face.getId() == null) {
                    f.setId(HashUtil.generateId());
                } else {
                    f.setId(face.getId());
                }
                f.setName(face.getName());
                f.setDescription(face.getDescription());
                f.setType(face.getType());
                f.setManaCost(face.getManaCost());
                f.setPower(face.getPower());
                f.setToughness(face.getToughness());
                f.setArtist(face.getArtist());
                f.setColors(face.getColors());

                if (face.getImages() != null) {
                    ImagesEmbeddable fi = new ImagesEmbeddable();
                    fi.setSmall(face.getImages().getSmall());
                    fi.setNormal(face.getImages().getNormal());
                    fi.setLarge(face.getImages().getLarge());
                    fi.setPng(face.getImages().getPng());
                    fi.setArtCrop(face.getImages().getArtCrop());
                    fi.setBorderCrop(face.getImages().getBorderCrop());
                    f.setImages(fi);
                }

                f.setCard(entity);
                return f;
            }).collect(Collectors.toList()));
        }

        return entity;
    }

    public static Card fromCardEntityToDomain(CardEntity entity) {
        if (entity == null) {
            return null;
        }

        return Card.builder()
                .id(entity.getId())
                .scryfallId(entity.getScryfallId())
                .name(entity.getName())
                .description(entity.getDescription())
                .type(entity.getType())
                .manaCost(entity.getManaCost())
                .totalManaCost(entity.getTotalManaCost())
                .power(entity.getPower())
                .toughness(entity.getToughness())
                .rarity(entity.getRarity())
                .artist(entity.getArtist())
                .colors(entity.getColors())
                .colorIdentity(entity.getColorIdentity())
                .images(entity.getImages() != null ? ImagesMapper.fromEmbeddableToDomain(entity.getImages()) : null)
                .faces(entity.getFaces() != null
                        ? entity.getFaces().stream()
                        .map(FaceMapper::fromEntityToDomain)
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }


    public static List<Card> fromCardEntityToDomainList(List<CardEntity> entityList) {
        if (entityList == null) {
            return Collections.emptyList();
        }
        return entityList.stream()
                .map(CardMapper::fromCardEntityToDomain)
                .collect(Collectors.toList());
    }

    public static List<Card> toDomainList(List<ScryfallCardDto> cards) {
        if (cards == null) {
            return Collections.emptyList();
        }
        return cards.stream()
                .map(CardMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static List<CardEntity> toEntityList(List<Card> cards) {
        if (cards == null) {
            return Collections.emptyList();
        }
        return cards.stream()
                .map(CardMapper::toEntity)
                .collect(Collectors.toList());
    }
}
