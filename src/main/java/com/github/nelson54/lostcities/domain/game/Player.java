package com.github.nelson54.lostcities.domain.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.game.board.Board;

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

    public void play(Card card) {
        hand.remove(card);
        board.play(card);
    }

    public void discard(Card card) {
        hand.remove(card);
        game.discard(card);
    }

    public void draw() {
        Card drew = game.draw();
        hand.add(drew);
    }

    public void draw(Color color) {
        Card drew = game.draw(color);
        hand.add(drew);
    }

    public GameUser getGameUser() {
        return gameUser;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
