package com.github.nelson54.lostcities.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.nelson54.lostcities.domain.game.Command;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommandDto {
    private Long id;
    private String color;
    private String play;
    private String discard;
    private Long gameUserId;

    public static CommandDto of(Command command) {
        CommandDto dto = new CommandDto();

        if(command.getDrawColor() != null) {
            dto.setColor(command.getDrawColor().toSring());
        }

        if(command.getPlay() != null) {
            dto.setPlay(command.getPlay().toString());
        }

        if(command.getDiscard() != null) {
            dto.setDiscard(command.getDiscard().toString());
        }

        dto.setGameUserId(command.getPlayer().getGameUser().getId());

        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public String getDiscard() {
        return discard;
    }

    public void setDiscard(String discard) {
        this.discard = discard;
    }

    public Long getGameUserId() {
        return gameUserId;
    }

    public void setGameUserId(Long gameUserId) {
        this.gameUserId = gameUserId;
    }
}
