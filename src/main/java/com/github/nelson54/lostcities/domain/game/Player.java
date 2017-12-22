package com.github.nelson54.lostcities.domain.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.game.board.Board;
import com.github.nelson54.lostcities.domain.game.exceptions.GameException;
import com.github.nelson54.lostcities.domain.game.exceptions.UnableToPlayCardException;

import java.util.HashSet;
import java.util.Set;

public class Player {

    private final GameUser gameUser;

    @JsonIgnore
    private Game game;

    @JsonProperty
    private final Set<Card> hand;

    @JsonProperty
    private final Board board;

    public Player(GameUser gameUser) {
        this.gameUser = gameUser;
        this.hand = new HashSet<>();
        this.board = Board.create();
    }

    public void play(Card card) throws GameException {
        if(hand.contains(card)) {
            hand.remove(card);
            board.play(card);
        } else {
            throw new UnableToPlayCardException(card);
        }
    }

    public void discard(Card card) {
        hand.remove(card);
        game.discard(card);
    }

    public Card draw() {
        Card drew = game.draw();
        hand.add(drew);
        return drew;
    }

    public Card draw(Color color) {
        Card drew = game.draw(color);
        hand.add(drew);
        return drew;
    }

    public Set<Card> getHand() {
        return hand;
    }

    public GameUser getGameUser() {
        return gameUser;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Board getBoard() {
        return board;
    }
}
