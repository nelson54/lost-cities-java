package com.github.nelson54.lostcities.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.game.Card;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.Player;
import com.github.nelson54.lostcities.domain.game.board.Board;

import java.util.Set;

public class PlayerViewDto {
    @JsonProperty
    private Long matchId;

    @JsonProperty
    private Long userId;

    @JsonProperty
    private Long gameUserId;

    @JsonProperty
    private String login;

    @JsonProperty
    private Boolean isActivePlayer;

    @JsonProperty
    private Set<Card> hand;

    @JsonProperty
    private Board board;

    @JsonProperty
    private Board discard;

    public static PlayerViewDto create(GameUser gameUser, Game game) {
        PlayerViewDto dto = new PlayerViewDto();
        Player player = game.getPlayer(gameUser.getId());

        dto.login = gameUser.getUser().getLogin();
        dto.matchId = game.getMatch().getId();
        dto.userId = player.getGameUser().getUser().getId();
        dto.gameUserId = player.getGameUser().getId();

        dto.isActivePlayer = game.currentPlayer().equals(player);

        dto.hand = player.getHand();
        dto.board = player.getBoard();
        dto.discard = game.getDiscard();

        return dto;
    }

    public Long getMatchId() {
        return matchId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGameUserId() {
        return gameUserId;
    }

    public String getLogin() {
        return login;
    }

    public Boolean getActivePlayer() {
        return isActivePlayer;
    }

    public Set<Card> getHand() {
        return hand;
    }

    public Board getBoard() {
        return board;
    }

    public Board getDiscard() {
        return discard;
    }
}


