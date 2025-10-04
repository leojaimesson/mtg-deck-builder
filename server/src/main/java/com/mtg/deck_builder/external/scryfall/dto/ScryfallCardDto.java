package com.mtg.deck_builder.external.scryfall.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScryfallCardDto {
    private String id;
    private String name;
    @JsonProperty("type_line")
    private String typeLine;
    @JsonProperty("oracle_text")
    private String oracleText;
    private String rarity;
    private String artist;
    @JsonProperty("mana_cost")
    private String manaCost;
    private String cmc;
    private String power;
    private String toughness;
    private List<String> colors;
    @JsonProperty("color_identity")
    private List<String> colorIdentity;
    @JsonProperty("image_uris")
    private ScryfallImagesDto images;
    @JsonProperty("card_faces")
    private List<ScryfallFaceDto> faces;

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

    public String getTypeLine() {
        return typeLine;
    }

    public void setTypeLine(String typeLine) {
        this.typeLine = typeLine;
    }

    public String getOracleText() {
        return oracleText;
    }

    public void setOracleText(String oracleText) {
        this.oracleText = oracleText;
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

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getCmc() {
        return cmc;
    }

    public void setCmc(String cmc) {
        this.cmc = cmc;
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

    public ScryfallImagesDto getImages() {
        return images;
    }

    public void setImages(ScryfallImagesDto images) {
        this.images = images;
    }

    public List<ScryfallFaceDto> getFaces() {
        return faces;
    }

    public void setFaces(List<ScryfallFaceDto> faces) {
        this.faces = faces;
    }
}
