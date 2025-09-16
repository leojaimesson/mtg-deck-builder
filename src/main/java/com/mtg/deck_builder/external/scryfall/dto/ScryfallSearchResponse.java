package com.mtg.deck_builder.external.scryfall.dto;

import java.util.List;

public class ScryfallSearchResponse {
    private String object;
    private int total_cards;
    private boolean has_more;
    private List<ScryfallCardDto> data;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public int getTotal_cards() {
        return total_cards;
    }

    public void setTotal_cards(int total_cards) {
        this.total_cards = total_cards;
    }

    public boolean isHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public List<ScryfallCardDto> getData() {
        return data;
    }

    public void setData(List<ScryfallCardDto> data) {
        this.data = data;
    }
}