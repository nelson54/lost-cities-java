package com.github.nelson54.lostcities.domain.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Command {
    @JsonProperty
    private final Player player;

    @JsonProperty
    private final Color drawColor;

    @JsonProperty
    private final Card play;

    @JsonProperty
    private final Card discard;

    @JsonProperty
    private Card drew;

    public Command(Player player, Color drawColor, Card play, Card discard, Card drew) {
        this.player = player;
        this.drawColor = drawColor;
        this.play = play;
        this.discard = discard;
        this.drew = drew;
    }

    public Command(Player player, Color drawColor, Card play, Card discard) {
        this.player = player;
        this.drawColor = drawColor;
        this.play = play;
        this.discard = discard;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getDrawColor() {
        return drawColor;
    }

    public Card getPlay() {
        return play;
    }

    public Card getDiscard() {
        return discard;
    }

    public Card getDrew() {
        return drew;
    }

    public void setDrew(Card drew) {
        this.drew = drew;
    }
}
