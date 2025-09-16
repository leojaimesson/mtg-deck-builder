package com.mtg.deck_builder.cards.entitie;

public class Images {
    private final String small;
    private final String normal;
    private final String large;
    private final String png;
    private final String artCrop;
    private final String borderCrop;

    public String getSmall() {
        return small;
    }

    public String getNormal() {
        return normal;
    }

    public String getLarge() {
        return large;
    }

    public String getPng() {
        return png;
    }

    public String getArtCrop() {
        return artCrop;
    }

    public String getBorderCrop() {
        return borderCrop;
    }

    private Images(Builder builder) {
        this.small = builder.small;
        this.normal = builder.normal;
        this.large = builder.large;
        this.png = builder.png;
        this.artCrop = builder.artCrop;
        this.borderCrop = builder.borderCrop;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String small;
        private String normal;
        private String large;
        private String png;
        private String artCrop;
        private String borderCrop;

        public Builder small(String small) {
            this.small = small;
            return this;
        }

        public Builder normal(String normal) {
            this.normal = normal;
            return this;
        }

        public Builder large(String large) {
            this.large = large;
            return this;
        }

        public Builder png(String png) {
            this.png = png;
            return this;
        }

        public Builder artCrop(String artCrop) {
            this.artCrop = artCrop;
            return this;
        }

        public Builder borderCrop(String borderCrop) {
            this.borderCrop = borderCrop;
            return this;
        }

        public Images build() {
            return new Images(this);
        }
    }
}
