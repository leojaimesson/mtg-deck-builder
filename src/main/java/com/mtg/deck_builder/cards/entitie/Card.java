package com.mtg.deck_builder.cards.entitie;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    private String id;

    @Column(name = "scryfall_id")
    private String scryfallId;

    private String name;
    private String description;
    private String type;

    @Column(name = "mana_cost")
    private String manaCost;

    @Column(name = "total_mana_cost")
    private String totalManaCost;
    private String power;
    private String toughness;

    @ElementCollection
    @CollectionTable(name = "card_colors", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "color")
    private List<String> colors;

    @ElementCollection
    @CollectionTable(name = "card_color_identity", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "color_identity")
    private List<String> colorIdentity;

    private String rarity;
    private String artist;

    @Embedded
    private Images images;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Face> faces;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    protected Card() {
    }

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
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public String getId() { return id; }
    public String getScryfallId() { return scryfallId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getType() { return type; }
    public String getManaCost() { return manaCost; }
    public String getTotalManaCost() { return totalManaCost; }
    public String getPower() { return power; }
    public String getToughness() { return toughness; }
    public List<String> getColors() { return colors; }
    public List<String> getColorIdentity() { return colorIdentity; }
    public String getRarity() { return rarity; }
    public String getArtist() { return artist; }
    public Images getImages() { return images; }
    public List<Face> getFaces() { return faces; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

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
        private Instant createdAt;
        private Instant updatedAt;

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
        public Builder faces(List<Face> faces) {
            if (faces != null) {
                faces.forEach(face -> face.setCard(new Card(this)));
            }
            this.faces = faces;
            return this;
        }
        public Builder creadetAt(Instant createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(Instant updatedAt) { this.updatedAt = updatedAt; return this; }

        public Card build() {
            return new Card(this);
        }
    }
}
