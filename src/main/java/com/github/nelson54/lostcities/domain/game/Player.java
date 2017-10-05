package com.github.nelson54.lostcities.domain.game;

import com.github.nelson54.lostcities.domain.User;
import com.github.nelson54.lostcities.domain.game.board.Board;

import java.util.Set;

public class Player {

    private final User user;
    private final Game game;
    private final Set<Card> hand;
    private final Board board;

    public Player(User user, Game game, Set<Card> hand, Board board) {
        this.user = user;
        this.game = game;
        this.hand = hand;
        this.board = board;
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

    public User getUser() {
        return user;
    }
}
