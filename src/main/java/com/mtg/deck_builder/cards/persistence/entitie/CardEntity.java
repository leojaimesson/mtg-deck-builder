package com.mtg.deck_builder.cards.persistence.entitie;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cards")
public class CardEntity {
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
    private String rarity;
    private String artist;

    @ElementCollection
    @CollectionTable(name = "card_colors", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "color")
    private List<String> colors;

    @ElementCollection
    @CollectionTable(name = "card_color_identity", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "color_identity")
    private List<String> colorIdentity;

    @Embedded
    private ImagesEmbeddable images;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FaceEntity> faces;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScryfallId() {
        return scryfallId;
    }

    public void setScryfallId(String scryfallId) {
        this.scryfallId = scryfallId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getTotalManaCost() {
        return totalManaCost;
    }

    public void setTotalManaCost(String totalManaCost) {
        this.totalManaCost = totalManaCost;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getColorIdentity() {
        return colorIdentity;
    }

    public void setColorIdentity(List<String> colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    public ImagesEmbeddable getImages() {
        return images;
    }

    public void setImages(ImagesEmbeddable images) {
        this.images = images;
    }

    public List<FaceEntity> getFaces() {
        return faces;
    }

    public void setFaces(List<FaceEntity> faces) {
        this.faces = faces;
    }
}
