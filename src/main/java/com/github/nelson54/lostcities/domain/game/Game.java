package com.github.nelson54.lostcities.domain.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.lostcities.domain.game.board.Board;

import java.util.*;

public class Game {
    private final List<Player> players;
    @JsonProperty private final Board discard;
    private final Deque<Card> deck;
    private final long initialSeed;
    private final long currentSeed;
    private PlayerOrder currentPlayer;

    private Game(Long initialSeed, List<Player> players, Board board, List<Card> deck) {
        this.currentPlayer = PlayerOrder.PLAYER_1;
        this.players = players;
        this.discard = board;

        this.initialSeed = currentSeed = 0L;
        Random seed = new Random(this.initialSeed);
        Collections.shuffle(deck, seed);
        this.deck = new LinkedList<>();
        this.deck.addAll(deck);
    }

    public static Game create(Long initialSeed, Collection<Player> players) {
        List<Player> sortedPlayers = new LinkedList<>();
        sortedPlayers.addAll(players);
        Board board = Board.create();
        List<Card> deck = Card.buildDeck();

        Game game = new Game(initialSeed, sortedPlayers, board, deck);
        sortedPlayers.stream().forEach((player)-> player.setGame(game));
        return game;
    }

    @JsonProperty
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

    public Player currentPlayer() {
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
            .filter((player)-> player.getGameUser().getId().equals(id))
            .findFirst()
            .orElseThrow(()-> new RuntimeException("User not found."));
    }

    @JsonProperty
    public Integer remainingInDeck() {
        return deck.size();
    }
}
