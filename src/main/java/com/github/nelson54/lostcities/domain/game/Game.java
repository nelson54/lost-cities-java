package com.github.nelson54.lostcities.domain.game;

import com.github.nelson54.lostcities.domain.game.board.Board;

import java.util.*;

public class Game {
    private final List<Player> players;
    private final Board discard;
    private final Deque<Card> deck;
    private final long initialSeed;
    private final long currentSeed;
    private PlayerOrder currentPlayer;

    private Game(List<Player> players, Board board, Set<Card> deck) {
        this.currentPlayer = PlayerOrder.PLAYER_1;
        this.players = players;
        this.discard = board;
        this.deck = new LinkedList<>();
        this.deck.addAll(deck);
        initialSeed = currentSeed = 0L;

    }

    public static Game create(Collection<Player> players) {
        List<Player> sortedPlayers = new LinkedList<>();
        sortedPlayers.addAll(players);
        Board board = Board.create();
        Set<Card> deck = Card.buildDeck();

        return new Game(sortedPlayers, board, deck);
    }

    public boolean isOver() {
        return deck.isEmpty();
    }

    //Draw from deck
    public Card draw() {
        return deck.pollLast();
    }

    public Card draw(Color color) {
        return discard.draw(color);
    }

    public void discard(Card card) {
        discard.discard(card);
    }

    private Player currentPlayer() {
        return players.get(currentPlayer.getOrder());
    }

    public void incrementTurn() {
        currentPlayer = PlayerOrder.next(currentPlayer);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(Long id) {
        return players.stream()
            .filter((player)-> player.getUser().getId().equals(id))
            .findFirst()
            .orElseThrow(()-> new RuntimeException("User not found."));
    }


}
