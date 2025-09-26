package com.mtg.deck_builder.cards.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Images", description = "Represents available image URLs for a card")
public record ImagesResponseDto(

        @Schema(description = "Small size image URL", example = "https://img.scryfall.com/cards/small/front/4/1/4123abcd.jpg")
        String small,

        @Schema(description = "Normal size image URL", example = "https://img.scryfall.com/cards/normal/front/4/1/4123abcd.jpg")
        String normal,

        @Schema(description = "Large size image URL", example = "https://img.scryfall.com/cards/large/front/4/1/4123abcd.jpg")
        String large,

        @Schema(description = "PNG image URL", example = "https://img.scryfall.com/cards/png/front/4/1/4123abcd.png")
        String png,

        @Schema(description = "Art crop image URL", example = "https://img.scryfall.com/cards/art_crop/front/4/1/4123abcd.jpg")
        String artCrop,

        @Schema(description = "Border crop image URL", example = "https://img.scryfall.com/cards/border_crop/front/4/1/4123abcd.jpg")
        String borderCrop
) {}
