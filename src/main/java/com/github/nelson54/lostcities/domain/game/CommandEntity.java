package com.github.nelson54.lostcities.domain.game;

import com.github.nelson54.lostcities.domain.User;

public class CommandEntity {
    private final User user;
    private final String color;
    private final String play;
    private final String discard;

    public CommandEntity(User user, String color, String play, String discard) {
        this.user = user;
        this.color = color;
        this.play = play;
        this.discard = discard;
    }

    public User getUser() {
        return user;
    }

    public String getColor() {
        return color;
    }

    public String getPlay() {
        return play;
    }

    public String getDiscard() {
        return discard;
    }
}
