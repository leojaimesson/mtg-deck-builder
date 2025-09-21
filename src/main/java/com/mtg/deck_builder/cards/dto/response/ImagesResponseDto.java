package com.mtg.deck_builder.cards.dto.response;

public record ImagesResponseDto(
        String small,
        String normal,
        String large,
        String png,
        String artCrop,
        String borderCrop
) {}
