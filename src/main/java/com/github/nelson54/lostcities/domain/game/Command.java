package com.github.nelson54.lostcities.domain.game;

public class Command {
    private final Player player;
    private final Color drawColor;
    private final Card play;
    private final Card discard;

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
}
