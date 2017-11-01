package com.github.nelson54.lostcities.domain.game;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.game.board.Board;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private final List<Player> players;
    @JsonProperty private final Board discard;
    private final Deque<Card> deck;
    private final long initialSeed;
    private final long currentSeed;
    private PlayerOrder currentPlayer;
    private Match match;

    private Game(Match match, List<Player> players, Board board, List<Card> deck) {
        this.match = match;
        this.currentPlayer = PlayerOrder.PLAYER_1;
        this.players = players;
        this.discard = board;

        this.initialSeed = currentSeed = match.getInitialSeed();
        Random seed = new Random(this.initialSeed);
        Collections.shuffle(deck, seed);
        this.deck = new LinkedList<>();
        this.deck.addAll(deck);
    }

    public static Game create(Match match) {
        List<Player> sortedPlayers = new LinkedList<>();
        sortedPlayers.addAll(playersFromMatch(match));
        Board board = Board.create();
        List<Card> deck = Card.buildDeck();

        Game game = new Game(match, sortedPlayers, board, deck);
        sortedPlayers.forEach((player)-> player.setGame(game));
        return game;
    }

    public static List<Player> playersFromMatch(Match match) {
        return  match.getGameUsers()
            .stream()
            .map(Player::new)
            .collect(Collectors.toList());
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

    public Match getMatch() {
        return match;
    }

    @JsonProperty
    public Integer remainingInDeck() {
        return deck.size();
    }

    public Board getDiscard() {
        return discard;
    }

    public Deque<Card> getDeck() {
        return deck;
    }
}
