package com.example.demo.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.util.List;
@Builder
@EqualsAndHashCode
public class EligiblityResponse {

    private List<String> cards;
    public EligiblityResponse() {
    }

    public EligiblityResponse(List<String> cards) {
        this.cards = cards;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }
}
