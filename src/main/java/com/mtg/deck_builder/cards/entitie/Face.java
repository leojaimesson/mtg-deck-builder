package com.mtg.deck_builder.cards.entitie;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "faces")
public class Face {

    @Id
    private String id;

    private String name;
    private String description;
    private String type;

    @Column(name = "mana_cost")
    private String manaCost;

    private String power;
    private String toughness;

    @ElementCollection
    @CollectionTable(name = "face_colors", joinColumns = @JoinColumn(name = "face_id"))
    @Column(name = "color")
    private List<String> colors;

    private String artist;

    @Embedded
    private Images images;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    protected Face() {
    }

    private Face(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.type = builder.type;
        this.manaCost = builder.manaCost;
        this.power = builder.power;
        this.toughness = builder.toughness;
        this.colors = builder.colors;
        this.artist = builder.artist;
        this.images = builder.images;
        this.card = builder.card;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getManaCost() { return manaCost; }
    public String getPower() { return power; }
    public String getToughness() { return toughness; }
    public List<String> getColors() { return colors; }
    public String getArtist() { return artist; }
    public Images getImages() { return images; }
    public Card getCard() { return card; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private String description;
        private String type;
        private String manaCost;
        private String power;
        private String toughness;
        private List<String> colors;
        private String artist;
        private Images images;
        private Card card;

        public Builder id(String id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder manaCost(String manaCost) { this.manaCost = manaCost; return this; }
        public Builder power(String power) { this.power = power; return this; }
        public Builder toughness(String toughness) { this.toughness = toughness; return this; }
        public Builder colors(List<String> colors) { this.colors = colors; return this; }
        public Builder artist(String artist) { this.artist = artist; return this; }
        public Builder images(Images images) { this.images = images; return this; }
        public Builder card(Card card) { this.card = card; return this; }

        public Face build() {
            return new Face(this);
        }
    }
}
