package com.github.nelson54.lostcities.domain.game;

import org.junit.Assert;
import org.junit.Test;

public class CardTest {
    @Test
    public void parse() throws Exception {
    }

    @Test
    public void testToString() throws Exception {
        String cardStr = "RED{8}[0]instance";
        Card card = Card.parse(cardStr);

        Assert.assertEquals(card.toString(), cardStr);
    }

    @Test
    public void testHashCode() throws Exception {
        String cardStr = "RED{8}[0]instance";
        Card card = Card.parse(cardStr);

        Assert.assertEquals(card.toString(), cardStr);
    }

    @Test
    public void parseTest() throws Exception {
        Color color = Color.BLUE;
        Integer value = 1;
        Integer instance = 2;

        String formattedCard = String.format("%s{%d}[%d]instance", color, value, instance);

        Card card = Card.parse(formattedCard);

        Assert.assertEquals(card.toString(), formattedCard);


    }

}
