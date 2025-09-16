package com.mtg.deck_builder.cards.entitie;

import java.util.List;

public class Card {
    private final String id;
    private final String scryfallId;

    private final String name;
    private final String description;
    private final String type;
    private final String manaCost;
    private final String totalManaCost;
    private final String power;
    private final String toughness;
    private final List<String> colors;
    private final List<String> colorIdentity;
    private final String rarity;
    private final String artist;

    private final Images images;

    public String getId() {
        return id;
    }

    public String getScryfallId() {
        return scryfallId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getManaCost() {
        return manaCost;
    }

    public String getTotalManaCost() {
        return totalManaCost;
    }

    public String getPower() {
        return power;
    }

    public String getToughness() {
        return toughness;
    }

    public List<String> getColors() {
        return colors;
    }

    public List<String> getColorIdentity() {
        return colorIdentity;
    }

    public String getRarity() {
        return rarity;
    }

    public String getArtist() {
        return artist;
    }

    public Images getImages() {
        return images;
    }

    public List<Face> getFaces() {
        return faces;
    }

    private final List<Face> faces;

    private Card(Builder builder) {
        this.id = builder.id;
        this.scryfallId = builder.scryfallId;
        this.name = builder.name;
        this.description = builder.description;
        this.type = builder.type;
        this.manaCost = builder.manaCost;
        this.totalManaCost = builder.totalManaCost;
        this.power = builder.power;
        this.toughness = builder.toughness;
        this.colors = builder.colors;
        this.colorIdentity = builder.colorIdentity;
        this.rarity = builder.rarity;
        this.artist = builder.artist;
        this.images = builder.images;
        this.faces = builder.faces;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String scryfallId;
        private String name;
        private String description;
        private String type;
        private String manaCost;
        private String totalManaCost;
        private String power;
        private String toughness;
        private List<String> colors;
        private List<String> colorIdentity;
        private String rarity;
        private String artist;
        private Images images;
        private List<Face> faces;

        public Builder id(String id) { this.id = id; return this; }
        public Builder scryfallId(String scryfallId) { this.scryfallId = scryfallId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder manaCost(String manaCost) { this.manaCost = manaCost; return this; }
        public Builder totalManaCost(String totalManaCost) { this.totalManaCost = totalManaCost; return this; }
        public Builder power(String power) { this.power = power; return this; }
        public Builder toughness(String toughness) { this.toughness = toughness; return this; }
        public Builder colors(List<String> colors) { this.colors = colors; return this; }
        public Builder colorIdentity(List<String> colorIdentity) { this.colorIdentity = colorIdentity; return this; }
        public Builder rarity(String rarity) { this.rarity = rarity; return this; }
        public Builder artist(String artist) { this.artist = artist; return this; }
        public Builder images(Images images) { this.images = images; return this; }
        public Builder faces(List<Face> faces) { this.faces = faces; return this; }

        public Card build() { return new Card(this); }
    }
}
