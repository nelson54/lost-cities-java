package com.github.nelson54.lostcities.domain.game.mappers;

import com.github.nelson54.lostcities.domain.Match;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.Player;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MatchToGameMapper {

    public Game map(Match match){
        Set<Player> players = match.getGameUsers()
            .stream()
            .map(Player::new)
            .collect(Collectors.toSet());

        Game game = Game.create(match.getInitialSeed(), players);

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
