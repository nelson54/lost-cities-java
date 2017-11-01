package com.github.nelson54.lostcities.domain.game.exceptions;

import com.github.nelson54.lostcities.domain.game.Card;

public class UnableToPlayCardException extends GameException {

    public Card card;
    public String message;

    public UnableToPlayCardException(Card card) {
        this.card = card;
        this.message = String.format("Unable to play card that is not in hand %s", card.toString());
    }
}
