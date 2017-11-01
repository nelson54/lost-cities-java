package com.github.nelson54.lostcities.domain.game.mappers;

import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.Player;
import org.springframework.stereotype.Component;

@Component
public class MatchToGameMapper {

    public Game map(Match match){
        Game game = Game.create(match);

        drawHands(game);

        return game;
    }

    private void drawHands(Game game) {
        drawHand(game.currentPlayer());
        game.incrementTurn();
        drawHand(game.currentPlayer());
        game.incrementTurn();
    }

    private void drawHand(Player player) {
        player.draw();
        player.draw();
        player.draw();
        player.draw();
        player.draw();
        player.draw();
        player.draw();
        player.draw();
    }
}
