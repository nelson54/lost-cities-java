package com.github.nelson54.lostcities.domain.game;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {
    @Test
    public void parse() throws Exception {

        Card card = Card.parse("GREEN{5}[0]instance");

        Assert.assertNotNull(card);
    }

}
