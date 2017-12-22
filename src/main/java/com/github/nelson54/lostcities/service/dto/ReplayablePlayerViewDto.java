package com.github.nelson54.lostcities.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.nelson54.lostcities.domain.GameUser;
import com.github.nelson54.lostcities.domain.game.Card;
import com.github.nelson54.lostcities.domain.game.Command;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.Player;

import java.util.List;
import java.util.Set;

public class ReplayablePlayerViewDto {
    @JsonProperty
    private Long matchId;

    @JsonProperty
    private Long userId;

    @JsonProperty
    private Long gameUserId;

    @JsonProperty
    private String login;

    @JsonProperty
    private Set<Card> hand;

    @JsonProperty
    private List<Command> commands;


    public static ReplayablePlayerViewDto create(GameUser gameUser, Game game) {
        ReplayablePlayerViewDto dto = new ReplayablePlayerViewDto();
        Player player = game.getPlayer(gameUser.getId());

        dto.login = gameUser.getUser().getLogin();
        dto.matchId = game.getMatch().getId();
        dto.userId = player.getGameUser().getUser().getId();
        dto.gameUserId = player.getGameUser().getId();
        dto.commands = game.getCommands();
        dto.hand = player.getHand();

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

    public Set<Card> getHand() {
        return hand;
    }

    public List<Command> getCommands() {
        return commands;
    }
}
