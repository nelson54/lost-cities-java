package com.github.nelson54.lostcities.domain.game;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Card {
    private static final Pattern cardPattern = Pattern.compile("(YELLOW|BLUE|WHITE|GREEN|RED)\\{(\\d+)\\}\\[(\\d)\\]instance");

    @JsonProperty
    private final Integer instance;

    @JsonProperty
    private final Color color;

    @JsonProperty
    private final Integer value;

    public static Card parse(String cardString) {
        Matcher matcher = cardPattern.matcher(cardString);
        if(matcher.find()) {
            Color color = Color.parse(matcher.group(1));
            Integer value = Integer.parseInt(matcher.group(2));
            Integer instance = Integer.parseInt(matcher.group(3));

            return new Card(instance, color, value);
        } else {
            throw new RuntimeException("Unable to parse card: " + cardString);
        }
    }

    public static Card of(Integer instance, Color color, Integer value) {
        return new Card(instance, color, value);
    }

    private Card(Integer instance, Color color, Integer value) {
        this.instance = instance;
        this.color = color;
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public Integer getValue() {
        if(value == 1) {
            return 0;
        } else {
            return value;
        }
    }

    public Integer getInstance() {
        return instance;
    }

    public Boolean isMultiplier() {
        return value == 1;
    }

    protected static List<Card>  buildDeck() {
        List<Card> cards = new ArrayList<>();
        Color.stream().forEach((color)-> {
            cards.addAll(buildColor(color));
        });

        return cards;
    }

    private static List<Card> buildColor(Color color) {
        List<Card> cards =  IntStream.rangeClosed(2, 10)
            .mapToObj((i) -> new Card(0, color, i))
            .collect(Collectors.toList());

        cards.addAll(IntStream.rangeClosed(0, 2)
            .mapToObj((i) -> new Card(i, color, 1))
            .collect(Collectors.toList()));

        return cards;
    }

    @JsonProperty
    public String toString() {
        return String.format("%s{%d}[%d]instance", color, value, instance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!instance.equals(card.instance)) return false;
        if (color != card.color) return false;
        return value.equals(card.value);
    }

    @Override
    public int hashCode() {
        int result = instance.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }
}
