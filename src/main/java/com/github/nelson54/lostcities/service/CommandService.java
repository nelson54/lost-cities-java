package com.github.nelson54.lostcities.service;

import com.github.nelson54.lostcities.domain.game.Card;
import com.github.nelson54.lostcities.domain.game.Command;
import com.github.nelson54.lostcities.domain.game.Game;
import com.github.nelson54.lostcities.domain.game.Player;
import org.springframework.stereotype.Service;

@Service
public class CommandService {

    public void execute(Game game, Command command) {
        Player player = command.getPlayer();
        Card drew;

        if (command.getPlay() != null) {
            player.play(command.getPlay());
        } else if (command.getDiscard() != null) {
            player.discard(command.getDiscard());

        }

        // Draw from discard
        if (command.getDrawColor() != null) {
            player.draw(command.getDrawColor());
        //Draw from deck
        } else {
            player.draw();
        }

        game.incrementTurn();
    }
}
