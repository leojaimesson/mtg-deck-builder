package com.mtg.deck_builder.cards.persistence.entitie;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ImagesEmbeddable {
    private String small;
    private String normal;
    private String large;
    private String png;
    @Column(name = "art_crop")
    private String artCrop;
    @Column(name = "border_crop")
    private String borderCrop;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }

    public String getArtCrop() {
        return artCrop;
    }

    public void setArtCrop(String artCrop) {
        this.artCrop = artCrop;
    }

    public String getBorderCrop() {
        return borderCrop;
    }

    public void setBorderCrop(String borderCrop) {
        this.borderCrop = borderCrop;
    }
}
