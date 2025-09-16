package com.mtg.deck_builder.cards.persistence.entitie;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "faces")
public class FaceEntity {
    @Id
    private String id;

    private String name;
    private String description;
    private String type;
    @Column(name = "mana_cost")
    private String manaCost;
    private String power;
    private String toughness;
    private String artist;

    @ElementCollection
    @CollectionTable(name = "face_colors", joinColumns = @JoinColumn(name = "face_id"))
    @Column(name = "color")
    private List<String> colors;

    @Embedded
    private ImagesEmbeddable images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ImagesEmbeddable getImages() {
        return images;
    }

    public void setImages(ImagesEmbeddable images) {
        this.images = images;
    }

    public CardEntity getCard() {
        return card;
    }

    public void setCard(CardEntity card) {
        this.card = card;
    }

    @ManyToOne
    @JoinColumn(name = "card_id")
    private CardEntity card;
}
