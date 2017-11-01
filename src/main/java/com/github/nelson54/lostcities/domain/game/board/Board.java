package com.github.nelson54.lostcities.domain.game.board;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.lostcities.domain.game.Card;
import com.github.nelson54.lostcities.domain.game.Color;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Board {
    @JsonProperty
    private final Map<Color, Deque<Card>> cards;

    private Board(Map<Color, Deque<Card>> cards) {
        this.cards = cards;
    }

    public static Board create() {
        Map<Color, Deque<Card>> cards = new HashMap<>();

        Color.stream().forEach((color -> cards.put(color, new LinkedList<>())));

        return new Board(cards);
    }

    public Card draw(Color color) {
        return cards.get(color).pollFirst();
    }

    public void discard(Card card) {
        getColor(card).offerFirst(card);
    }

    public void play(Card card) {
        if(!isValidPlay(card) && false) {
            throw new IllegalPlayException();
        } else {
            getColor(card).offerLast(card);
        }
    }

    private boolean isValidPlay (Card card) {
        Deque<Card> cards = getColor(card);

        return cards.isEmpty() || cards.peekLast().getValue() <= card.getValue();
    }

    private Deque<Card> getColor(Card card) {
        return cards.get(card.getColor());
    }
}
