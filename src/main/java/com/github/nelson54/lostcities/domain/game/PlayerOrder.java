package com.github.nelson54.lostcities.domain.game;

public enum PlayerOrder {
    PLAYER_1(0),
    PLAYER_2(1);

    private final int order;

    PlayerOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public static PlayerOrder next(PlayerOrder currentPlayer) {
        if(currentPlayer.order == 0) {
            return PLAYER_2;
        } else {
            return PLAYER_1;
        }
    }
}
